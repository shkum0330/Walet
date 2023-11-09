package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.api.request.account.AssignRequest;
import com.ssafy.account.api.request.account.SelectChargingAccountRequest;
import com.ssafy.account.api.response.account.*;
import com.ssafy.account.api.response.transaction.MonthlyExpenditureDetailResponse;
import com.ssafy.account.common.api.exception.DuplicatedException;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.access.Access;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.db.repository.AccessRepository;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccessRepository accessRepository;

    @Value("${hash.pepper}")
    private String pepper;

    // 일반계좌 발급
    @Override
    public Long registerGeneralAccount(AccountSaveRequest accountSaveRequest) {
        Account account = new Account(accountSaveRequest);

        // 사업자계좌면(accountType이 01이라면) 사업유형도 입력
        if(accountSaveRequest.getAccountType().equals("01")) {
            account.addBusinessType(accountSaveRequest.getBusinessType());
        }

        // todo: salt+pepper 추가
        String hashPassword = hashPassword(accountSaveRequest.getAccountPwd());
        account.addHashPwd(hashPassword);

        // 우선 랜덤으로 13자리의 계좌번호 부여
        int length = 13;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        String accountNumber = sb.toString();
        account.createAccountNumber(accountNumber);

        log.info("게좌 정보: {}",account.toString());
        // 계좌 정보를 DB에 저장
        accountRepository.save(account);
        return account.getId();
    }

    // 동물계좌 발급
    // 동물계좌 생성 버튼 클릭 -> 모계좌를 연결할 사람은 기존에 있던 농협 계좌 중 선택(필수사항x) -> 내 반려동물 정보 입력 -> 동물계좌 완성
    // cf) 계좌를 등록하려고 할 때 내가 등록할 비문 사진이 이미 있으면 양도 받는 것이 목적이냐고 물어보기
    @Override
    public Long registerPetAccount(PetAccountSaveRequest petAccountRequest) {

        // 펫계좌와 관련된 정보가 입력되었을 때
        // 입력된 비문 또는 RFID코드가 이미 등록돼있다면
        // 이미 등록된 계좌가 있으니 양도신청 알림을 보낼거냐는 팝업창을 띄워줌
        // 확인을 누르면 해당 계좌의 주인한테 알림 메시지를 보냄


        Account petAccount = new Account(petAccountRequest);

        String hashRfidCode = hashPassword(petAccountRequest.getRfidCode());
        petAccount.addHashedRfid(hashRfidCode);
        // todo: salt+pepper 추가
        String hashPassword = hashPassword(petAccountRequest.getAccountPwd());
        petAccount.addHashPwd(hashPassword);

        // 제한업종 추가
        List<Integer> limitTypeList = petAccountRequest.getLimitTypeIdList();
        log.info("제한업종: {}",limitTypeList);
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

        // 우선 랜덤으로 13자리의 계좌번호 부여
        int length = 13;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        String accountNumber = sb.toString();
        petAccount.createAccountNumber(accountNumber);

        // 계좌 정보를 DB에 저장
        accountRepository.save(petAccount);
        return petAccount.getId();
    }
    
    public static String hashPassword(String password) {
        // 비밀번호 해쉬화
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                String hex = Integer.toHexString(0xff & hashedByte);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    // 충전계좌로 등록할 수 있는 계좌 리스트 반환(일반계좌만 반환)
    @Override
    public List<ChargingAccountResponse> getChargingAccountList(Long memberId) {
        // 해당 유저의 계좌 중에
        // 일반 계좌만 리스트로 가져오자
        List<Account> allAccounts = accountRepository.findAccountsByMemberIdAndAccountType(memberId, "00");
        List<ChargingAccountResponse> result = allAccounts.stream().map((account ->
                new ChargingAccountResponse(account))).collect(Collectors.toList());
        return result;
    }

    // 사용자가 접근 허용된 펫계좌 리스트 반환
    @Override
    public List<AccessiblePetAccountResponse> getAccessibleAccountList(Long memberId) {
        List<Access> myAccessList = accessRepository.findAccessesByRequestMemberIdAndIsConfirmed(memberId, 1);
        // 접근이 허용된 펫계좌가 없다면 예외 발생
        if(myAccessList.isEmpty()) {
            throw new NotFoundException(NO_ACCESSIBLE_PET_ACCOUNT);
        }
        
        List<Account> myAccessiblePetAccount = new ArrayList<>();
        // 내가 접근 가능한 계좌 목록을 가져옴
        for (Access access : myAccessList) {
            Account petAccount = accountRepository.findAccountByPetNameAndAccountNumber(access.getPetName(), access.getAccountNumber());
            myAccessiblePetAccount.add(petAccount);
        }

        return myAccessiblePetAccount.stream().map((account) ->
                new AccessiblePetAccountResponse(account)).collect(Collectors.toList());
    }

    // 관리자페이지용 - 관리자 페이지용 메소드가 굳이 여기에 있어야할까?
    // 선택된 유저의 모든 계좌 목록을 반환
    @Override
    public List<AccountResponse> getAllAccountList(Long memberId) {
        List<Account> allAccounts = accountRepository.findAccountsByMemberId(memberId);
        List<AccountResponse> result = allAccounts.stream().map(
                account -> new AccountResponse(account)
        ).collect(Collectors.toList());
        return result;
    }

    // 충전계좌 선택
    @Override
    public Long selectChargingAccount(SelectChargingAccountRequest request) {
        Account myAccount = accountRepository.findById(request.getMyAccountId()).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 이미 등록돼있는 충전 계좌라면 예외발생
        Long myLinkedAccountId = myAccount.getLinkedAccountId();
        Long chargingAccountId = request.getChargingAccountId();
        if(myLinkedAccountId != null && myLinkedAccountId.equals(chargingAccountId)) {
            throw new DuplicatedException(DUPLICATED_LINKED_ACCOUNT);
        }
        
        // 아니라면 충전계좌로 연결해주자
        myAccount.addLinkedAccount(chargingAccountId);

        return chargingAccountId;
    }

    // 동물계좌 양도
    // 상대방 반려동물 계좌 생성 -> 이전 주인의 반려동물 계좌에서 돈 전부 이체 -> 이전 주인의 반려동물 계좌 삭제(폐쇄 상태로 변경)
    // 잔액을 양도 받은 계좌의 잔액 반환
    @Override
    public Long assignAccount(AssignRequest assignRequest) {
        Account transferAccount = accountRepository.findById(assignRequest.getTransfererAccountId()).orElseThrow(() -> new NotFoundException(NO_TRANSFER_ACCOUNT));
        Account transfereeAccount = accountRepository.findById(assignRequest.getTransfereeAccountId()).orElseThrow(() -> new NotFoundException(NO_TRANSFEREE_ACCOUNT));

        // 계좌 잔액을 이양
        Long balance = transferAccount.getBalance();
        if(balance > 0) {
            transferAccount.minusBalance(balance);
            transfereeAccount.addBalance(balance);
        }
        
        // 양도자의 계좌를 폐쇄상태로 변경
        transferAccount.updateStateToClosed();
        return transfereeAccount.getBalance();
    }

    // 계좌 상태 변경
    // 1. 정상
    @Override
    public void updateStateToActive(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToActive();
    }

    // 2. 잠금
    @Override
    public void updateStateToLocked(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToLocked();
    }

    // 3. 정지
    @Override
    public void updateStateToSuspended(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToSuspended();
    }

    // 4. 폐쇄
    @Override
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
    @Override
    public MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> byMyAccountId = transactionRepository.findByAccount(account);

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
                }).collect(Collectors.toList());

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
                }).collect(Collectors.toList());
        
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
    @Override
    public Map<String, String> getCategoryExpenditureDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> byMyAccountId = transactionRepository.findByAccount(account);

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

    @Override
    public Account findPetAccountByAccountId(Long memberId) {
        return accountRepository.findByMemberIdAndAccountType(memberId,"02").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
    }

    @Override
    public Account findPetAccountByDepositorName(String depositorName) {
        return accountRepository.findByDepositorNameAndAccountType(depositorName,"02").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
    }

    public Account findPetAccountByMemberId(Long memberId) {
        return accountRepository.findAccountsByMemberIdAndAccountType(memberId,"02").get(0);
    }

    public List<AdminMemberAccountResponse> findMemberAccount(Long memberId) {
        List<Account> memberAccountList = accountRepository.findAccountsByMemberId(memberId);
        return memberAccountList.stream().map((account) ->
                new AdminMemberAccountResponse(account)).collect(Collectors.toList());
    }

    @Override
    public AdminAccountCountResponse countAllAccountForAdmin() {
        long generalAccountCount = accountRepository.countByAccountType("00");
        long petAccountCount = accountRepository.countByAccountType("02");
        return new AdminAccountCountResponse(generalAccountCount, petAccountCount);
    }

    @Override
    public List<Account> findActiveAccountByMemberId(Long memberId, String accountType) {
        return accountRepository.findAccountByMemberIdAndAccountTypeAndAccountState(memberId,accountType,"00");
    }
}
