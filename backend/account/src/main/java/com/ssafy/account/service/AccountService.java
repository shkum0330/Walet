package com.ssafy.account.service;

import com.ssafy.account.api.request.account.*;
import com.ssafy.account.api.response.account.*;
import com.ssafy.account.api.response.transaction.MonthlyExpenditureDetailResponse;
import com.ssafy.account.common.api.exception.DuplicatedException;
import com.ssafy.account.common.api.exception.GlobalRuntimeException;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.access.Access;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.account.GeneralAccount;
import com.ssafy.account.db.entity.account.PetAccount;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.db.repository.AccessRepository;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.external.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.account.common.api.status.FailCode.*;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccessRepository accessRepository;
    private final OauthService oauthService;

    // 일반계좌 발급
    @Transactional
    public GeneralAccount registerGeneralAccount(Long memberId, AccountSaveRequest accountSaveRequest) {

        // 사업자 계좌인데 사업 유형이 없으면 에러
        if(accountSaveRequest.getAccountType().equals("01") && accountSaveRequest.getBusinessType() == null) {
            throw new GlobalRuntimeException(NO_BUSINESS_TYPE);
        }
        // 사업자 계좌가 아닌데 사업 유형이 있으면 에러
        if(!accountSaveRequest.getAccountType().equals("01") && accountSaveRequest.getBusinessType() != null) {
            throw new GlobalRuntimeException(UNNECESSARY_BUSINESS_TYPE);
        }

        GeneralAccount account = GeneralAccount.builder()
                .memberId(memberId)
                .depositorName(oauthService.getUserName(memberId)) // 회원 서버에서 이름 받아오기
                .accountSaveRequest(accountSaveRequest)
                .build();

        // 계좌 정보를 DB에 저장
        return accountRepository.save(account);
    }

    // 펫계좌 발급
    // 펫계좌 생성 버튼 클릭 -> 모계좌를 연결할 사람은 기존에 있던 농협 계좌 중 선택(필수사항x) -> 내 반려동물 정보 입력 -> 동물계좌 완성
    // cf) 계좌를 등록하려고 할 때 내가 등록할 비문 사진이 이미 있으면 양도 받는 것이 목적이냐고 물어보
    @Transactional
    public PetAccount registerPetAccount(Long memberId, PetAccountSaveRequest petAccountSaveRequest) {

        // 펫계좌와 관련된 정보가 입력되었을 때
        // 입력된 비문 또는 RFID코드가 이미 등록돼있다면
        // 이미 등록된 계좌가 있으니 양도신청 알림을 보낼거냐는 팝업창을 띄워줌
        // 확인을 누르면 해당 계좌의 주인한테 알림 메시지를 보냄
        PetAccount petAccount= PetAccount.builder()
                                    .memberId(memberId)
                                    .memberName(oauthService.getUserName(memberId))
                                    .petAccountSaveRequest(petAccountSaveRequest)
                                    .build();


        // 제한업종 추가
        List<Integer> limitTypeList = petAccountSaveRequest.getLimitTypeIdList();
        // 선택을 안했다면 전부 들어감
        if(limitTypeList.isEmpty()) {
            for(int i = 0; i < 5; i++) {
                petAccount.addLimitType(1 << i);
            }
        }
        // 선택을 했다면 용도 제한
        else {
            for (int limitType : limitTypeList) {
                petAccount.addLimitType(1 << (limitType-1));
            }
        }

        return  accountRepository.save(petAccount);
    }

    // 충전계좌로 등록할 수 있는 계좌 리스트 반환(일반계좌만 반환)
    public List<ChargingAccountResponse> getChargingAccountList(Long memberId) {
        // 해당 유저의 계좌 중 일반 계좌만 리스트로 가져옴
        List<Account> allAccounts = accountRepository.findAccountsByMemberIdAndAccountType(memberId, "00");
        return allAccounts.stream().map(ChargingAccountResponse::new).collect(toList());
    }

    // 사용자가 접근 허용된 펫계좌 리스트 반환
    public List<AccessiblePetAccountResponse> getAccessibleAccountList(Long memberId) {
        List<Access> myAccessList = accessRepository.findByRequestMemberIdAndIsConfirmed(memberId, 1);
        // 접근이 허용된 펫계좌가 없다면 예외 발생
        if(myAccessList.isEmpty()) {
            throw new NotFoundException(NO_ACCESSIBLE_PET_ACCOUNT);
        }
        
        List<Account> myAccessiblePetPetAccount = new ArrayList<>();
        // 내가 접근 가능한 계좌 목록을 가져옴
        for (Access access : myAccessList) {
            Account petAccount = accountRepository.findByPetNameAndAccountNumber(access.getPetName(), access.getAccountNumber());
            myAccessiblePetPetAccount.add(petAccount);
        }

        return myAccessiblePetPetAccount.stream().map(AccessiblePetAccountResponse::new).collect(toList());
    }

    // 선택된 유저의 모든 계좌 목록을 반환
    public List<AccountResponse> getAllAccountList(Long memberId) {
        List<Account> allPetAccounts = accountRepository.findAccountsByMemberId(memberId);
        return allPetAccounts.stream().map(AccountResponse::new).collect(toList());
    }

    // 충전계좌 선택
    @Transactional
    public Long selectChargingAccount(SelectChargingAccountRequest request) {
        Account account = accountRepository.findById(request.getMyAccountId()).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 이미 등록돼있는 충전 계좌라면 예외발생
        Long myLinkedAccountId = account.getLinkedAccountId();
        Long chargingAccountId = request.getChargingAccountId();
        if(myLinkedAccountId != null && myLinkedAccountId.equals(chargingAccountId)) {
            throw new DuplicatedException(DUPLICATED_LINKED_ACCOUNT);
        }
        
        // 아니라면 충전계좌로 연결해주자
        account.addLinkedAccount(chargingAccountId);

        return chargingAccountId;
    }

    // 동물계좌 양도
    // 상대방 반려동물 계좌 생성 -> 이전 주인의 반려동물 계좌에서 돈 전부 이체 -> 정보 이전 및 이전 주인의 반려동물 계좌 삭제(폐쇄 상태로 변경)
    // 잔액을 양도 받은 계좌의 잔액 반환
    // todo: TransferService로 이동
    @Transactional
    public Long assignAccount(AssignRequest assignRequest) {
        Account transferorPetAccount = accountRepository.findById(assignRequest.getTransferorAccountId()).orElseThrow(() -> new NotFoundException(NO_TRANSFER_ACCOUNT));
        Account transfereePetAccount = accountRepository.findById(assignRequest.getTransfereeAccountId()).orElseThrow(() -> new NotFoundException(NO_TRANSFEREE_ACCOUNT));
        log.info("양도인 pk:{} 양수인 pk:{}",assignRequest.getTransferorAccountId(),assignRequest.getTransfereeAccountId());
        // 계좌 잔액을 이양
        Long balance = transferorPetAccount.getBalance();
        if(balance > 0) {
            transferorPetAccount.minusBalance(balance);
            transfereePetAccount.addBalance(balance);
        }

        // 정보 이전
        if(transfereePetAccount instanceof PetAccount transferee && transferorPetAccount instanceof PetAccount transferor) {
            transferee.transferPetInfo(transferor);
            transferor.deletePetInfo();
            transferor.updateStateToClosed();
        }
        return balance;
    }

    // 계좌 상태 변경
    // 1. 정상
    @Transactional
    public void updateStateToActive(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToActive();
    }

    // 2. 잠금
    @Transactional
    public void updateStateToLocked(Long accountId) {
        Account petAccount = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        petAccount.updateStateToLocked();
    }

    // 3. 정지
    @Transactional
    public void updateStateToSuspended(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToStopped();
    }

    // 4. 폐쇄
    @Transactional
    public void updateStateToClosed(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToClosed();
    }

    // 이번 달 동물계좌 지출내역 표시(5가지 카테고리 전부)
    // 이전 달 총 지출액도 함께 보내줌
    // 반려동물 관련 업종 카테고리
    //  1. 동물병원
    //  2. 반려동물용품
    //  3. 반려동물미용
    //  4. 애견카페
    //  5. 반려견놀이터
    public MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId) {
        Account petAccount = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> byMyAccountId = transactionRepository.findByAccount(petAccount);

        // 이번 달 반려동물 관련 업종 총 소비
        Long currentTotalPetHospitalCost = 0L;
        Long currentTotalPetSupplyCost = 0L;
        Long currentTotalPetBeautyCost = 0L;
        Long currentTotalPetCafeCost = 0L;
        Long currentTotalPetPlayGroundCost = 0L;

        // 이번 달 거래내역을 가져오자
        LocalDate currentDate = LocalDate.now();
        LocalDate startDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        List<Transaction> currentMonthTransactions = byMyAccountId.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getTransactionTime().toLocalDate();
                    return transactionDate.isAfter(startDayOfMonth.minusDays(1)) && transactionDate.isBefore(currentDate.plusDays(1));
                }).collect(toList());

        // 이번 달의 총 지출액을 구해주자
        // 그리고 카테고리별 지출내역을 더해주자
        Long currentMonthTotalExpenditure = 1L;
        for (Transaction currentMonthTransaction : currentMonthTransactions) {
            if(currentMonthTransaction.getTransactionType() == TransactionType.WITHDRAWAL) {

                Long payment = currentMonthTransaction.getPaymentAmount();
                currentMonthTotalExpenditure += payment;

                Integer businessCategory = currentMonthTransaction.getBusinessCategory();
                switch (businessCategory) {
                    case 1:
                        currentTotalPetHospitalCost += payment;
                        break;
                    case 2:
                        currentTotalPetSupplyCost += payment;
                        break;
                    case 3:
                        currentTotalPetBeautyCost += payment;
                        break;
                    case 4:
                        currentTotalPetCafeCost += payment;
                        break;
                    case 5:
                        currentTotalPetPlayGroundCost += payment;
                        break;
                }
            }
        }

        // 카테고리별 지출액의 비중
        //  1. 동물병원
        //  2. 반려동물용품
        //  3. 반려동물미용
        //  4. 애견카페
        //  5. 반려견놀이터
        Map<String, Long> currentExpenditureByCategory = new HashMap<>();
        currentExpenditureByCategory.put("동물병원", currentTotalPetHospitalCost);
        currentExpenditureByCategory.put("반려동물용품", currentTotalPetSupplyCost);
        currentExpenditureByCategory.put("반려동물미용", currentTotalPetBeautyCost);
        currentExpenditureByCategory.put("애견카페", currentTotalPetCafeCost);
        currentExpenditureByCategory.put("반려견놀이터", currentTotalPetPlayGroundCost);
        
        // 저번 달 거래내역도 가져오자
        LocalDate startDayOfLastMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth().minus(1), 1);
        LocalDate endDayOfLastMonth = startDayOfLastMonth.plusMonths(1).minusDays(1);

        // 저번 달 반려동물 관련 업종 총 소비
        Long lastMonthTotalPetHospitalCost = 0L;
        Long lastMonthTotalPetSupplyCost = 0L;
        Long lastMonthTotalPetBeautyCost = 0L;
        Long lastMonthTotalPetCafeCost = 0L;
        Long lastMonthTotalPetPlayGroundCost = 0L;

        List<Transaction> lastMonthTransactions = byMyAccountId.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getTransactionTime().toLocalDate();
                    return transactionDate.isAfter(startDayOfLastMonth.minusDays(1)) && transactionDate.isBefore(endDayOfLastMonth.plusDays(1));
                }).collect(toList());
        
        // 저번 달의 카테고리 별 지출액을 구해주자
        for (Transaction lastMonthTransaction : lastMonthTransactions) {
            if(lastMonthTransaction.getTransactionType() == TransactionType.WITHDRAWAL) {

                Long payment = lastMonthTransaction.getPaymentAmount();

                Integer businessCategory = lastMonthTransaction.getBusinessCategory();
                switch (businessCategory) {
                    case 1:
                        lastMonthTotalPetHospitalCost += payment;
                        break;
                    case 2:
                        lastMonthTotalPetSupplyCost += payment;
                        break;
                    case 3:
                        lastMonthTotalPetBeautyCost += payment;
                        break;
                    case 4:
                        lastMonthTotalPetCafeCost += payment;
                        break;
                    case 5:
                        lastMonthTotalPetPlayGroundCost += payment;
                        break;
                }
            }
        }

        // 카테고리별 지출액의 비중
        //  1. 동물병원
        //  2. 반려동물용품
        //  3. 반려동물미용
        //  4. 애견카페
        //  5. 반려견놀이터
        Map<String, Long> lastMonthExpenditureByCategory = new HashMap<>();
        lastMonthExpenditureByCategory.put("동물병원", lastMonthTotalPetHospitalCost);
        lastMonthExpenditureByCategory.put("반려동물용품", lastMonthTotalPetSupplyCost);
        lastMonthExpenditureByCategory.put("반려동물미용", lastMonthTotalPetBeautyCost);
        lastMonthExpenditureByCategory.put("애견카페", lastMonthTotalPetCafeCost);
        lastMonthExpenditureByCategory.put("반려견놀이터", lastMonthTotalPetPlayGroundCost);

        Map<String, Long> expenditureChange = new HashMap<>();
        expenditureChange.put("동물병원", currentTotalPetHospitalCost - lastMonthTotalPetHospitalCost);
        expenditureChange.put("반려동물용품", currentTotalPetSupplyCost - lastMonthTotalPetSupplyCost);
        expenditureChange.put("반려동물미용", currentTotalPetBeautyCost - lastMonthTotalPetBeautyCost);
        expenditureChange.put("애견카페", currentTotalPetCafeCost - lastMonthTotalPetCafeCost);
        expenditureChange.put("반려견놀이터", currentTotalPetPlayGroundCost - lastMonthTotalPetPlayGroundCost);

        return new MonthlyExpenditureDetailResponse(currentMonthTotalExpenditure, currentExpenditureByCategory, lastMonthExpenditureByCategory, expenditureChange);
    }

    // 전체 기간에서 각 카테고리 별 지출 비율
    public Map<String, String> getCategoryExpenditureDetail(Long accountId) {
        Account petAccount = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> byMyAccountId = transactionRepository.findByAccount(petAccount);

        // 반려동물 관련 업종 총 소비
        Long totalPetHospitalCost = 0L;
        Long totalPetSupplyCost = 0L;
        Long totalPetBeautyCost = 0L;
        Long totalPetCafeCost = 0L;
        Long totalPetPlayGroundCost = 0L;

        Long totalExpenditure = 1L;
        for (Transaction transaction : byMyAccountId) {
            if(transaction.getTransactionType() == TransactionType.WITHDRAWAL) {

                Long payment = transaction.getPaymentAmount();
                totalExpenditure += payment;

                Integer businessCategory = transaction.getBusinessCategory();
                switch (businessCategory) {
                    case 1:
                        totalPetHospitalCost += payment;
                        break;
                    case 2:
                        totalPetSupplyCost += payment;
                        break;
                    case 3:
                        totalPetBeautyCost += payment;
                        break;
                    case 4:
                        totalPetCafeCost += payment;
                        break;
                    case 5:
                        totalPetPlayGroundCost += payment;
                        break;
                }
            }
        }

        Map<String, String> expenditureRatio = new HashMap<>();
        expenditureRatio.put("동물병원", (totalPetHospitalCost * 100 / totalExpenditure) + "%");
        expenditureRatio.put("반려동물용품", (totalPetSupplyCost * 100 / totalExpenditure) + "%");
        expenditureRatio.put("반려동물미용", (totalPetBeautyCost * 100 / totalExpenditure) + "%");
        expenditureRatio.put("애견카페", (totalPetCafeCost * 100 / totalExpenditure) + "%");
        expenditureRatio.put("반려견놀이터", (totalPetPlayGroundCost * 100 / totalExpenditure) + "%");

        return expenditureRatio;
    }


    public PetAccount findPetAccountByAccountId(Long memberId) {
        Account account=accountRepository.findByMemberIdAndAccountType(memberId,"02").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        return (PetAccount) account;
    }


    public PetAccount findPetAccountByDepositorName(String depositorName) {
        Account account = accountRepository.findByDepositorNameAndAccountType(depositorName,"02").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        return (PetAccount) account;
    }

    public PetAccount findPetAccountByMemberId(Long memberId) {
        Account account = accountRepository.findAccountsByMemberIdAndAccountType(memberId,"02").get(0);
        return (PetAccount) account;
    }

    public List<AdminMemberAccountResponse> findMemberAccount(Long memberId) {
        List<Account> memberPetAccountList = accountRepository.findAccountsByMemberId(memberId);
        return memberPetAccountList.stream().map(AdminMemberAccountResponse::new).collect(toList());
    }


    public List<AdminMemberAccountResponse> findAllMemberAccounts(AdminAllMemberIdsRequest request) {
        List<Long> allMemberIds = request.getAllMemberIds();
        List<AdminMemberAccountResponse> result = new ArrayList<>();

        for (Long memberId : allMemberIds) {
            List<Account> accountsByMemberId = accountRepository.findAccountsByMemberId(memberId);
            for (Account account : accountsByMemberId) {
                result.add(new AdminMemberAccountResponse(account));
            }
        }

        return result;
    }


    public AdminAccountCountResponse countAllAccountForAdmin() {
        long generalAccountCount = accountRepository.countByAccountType("00");
        long petAccountCount = accountRepository.countByAccountType("02");
        return new AdminAccountCountResponse(generalAccountCount, petAccountCount);
    }


    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
    }

    public Long countNewAccountInWeek() {
        LocalDateTime startOfDay = LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN);
        Long result = accountRepository.countByCreatedAtAfter(startOfDay);
        return result;
    }


    public List<Account> findActiveAccountByMemberId(Long memberId, String accountType) {
        return accountRepository.findAccountByMemberIdAndAccountTypeAndAccountState(memberId,accountType,"00");
    }


    public Account findAccountByAccountId(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
    }


    public Account findByRFID(String rfidCode) {
        return accountRepository.findByRfidCodeAndAccountState(rfidCode,"00").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
    }


    public Account findBusinessAccountByMemberId(Long memberID) {
        return accountRepository.findByMemberIdAndAccountType(memberID,"01").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
    }
}
