package com.example.account.service.impl;

import com.example.account.api.request.account.AccountSaveRequest;
import com.example.account.api.request.account.AnimalAccountSaveRequest;
import com.example.account.api.request.account.AssignRequest;
import com.example.account.api.request.account.SelectChargingAccountRequest;
import com.example.account.api.response.account.AccountResponse;
import com.example.account.api.response.account.ChargingAccountResponse;
import com.example.account.api.response.transaction.MonthlyExpenditureDetailResponse;
import com.example.account.common.api.exception.DuplicatedException;
import com.example.account.common.api.exception.NotFoundException;
import com.example.account.db.entity.account.Account;
import com.example.account.db.entity.transaction.Transaction;
import com.example.account.db.repository.AccountRepository;
import com.example.account.db.repository.TransactionRepository;
import com.example.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.account.common.api.status.FailCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // 일반계좌 발급
    @Override
    public Long registerGeneralAccount(AccountSaveRequest accountSaveRequest) {
        Account account = new Account(accountSaveRequest);

        // 사업자계좌면(accountType이 01이라면) 사업유형도 입력
        if(!accountSaveRequest.getAccountType().equals("01")) {
            account.addBusinessType(accountSaveRequest.getBusinessType());
        }

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
        
        // 계좌 정보를 DB에 저장
        accountRepository.save(account);
        return account.getId();
    }

    // 동물계좌 발급
    // 동물계좌 생성 버튼 클릭 -> 모계좌를 연결할 사람은 기존에 있던 농협 계좌 중 선택(필수사항x) -> 내 반려동물 정보 입력 -> 동물계좌 완성
    // cf) 계좌를 등록하려고 할 때 내가 등록할 비문 사진이 이미 있으면 양도 받는 것이 목적이냐고 물어보기
    @Override
    public Long registerAnimalAccount(AnimalAccountSaveRequest animalAccountRequest) {

        Account animalAccount = new Account(animalAccountRequest);

        String hashRfidCode = hashPassword(animalAccountRequest.getRfidCode());
        animalAccount.addHashedRfid(hashRfidCode);
        
        // 제한업종 추가
        List<Integer> limitTypeList = animalAccountRequest.getLimitTypeIdList();
        // 선택을 안했다면 전부 들어감
        if(limitTypeList.isEmpty()) {
            for(int i = 0; i < 6; i++) {
                animalAccount.addLimitType(1 << i);
            }
        }
        // 선택을 했다면 용도 제한
        else {
            for (int limitType : limitTypeList) {
                animalAccount.addLimitType(1 << (limitType-1));
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
        animalAccount.createAccountNumber(accountNumber);

        // 계좌 정보를 DB에 저장
        accountRepository.save(animalAccount);
        return animalAccount.getId();
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

    // 관리자페이지용
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

    // 이번 달 동물계좌 지출내역 표시(제한된 카테고리만)
    // 이전 달 총 지출액도 함께 보내줌
    @Override
    public MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> byMyAccountId = transactionRepository.findByAccount(account);

        LocalDate currentDate = LocalDate.now();

        // 이번 달 거래내역을 가져오자
        LocalDate startDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
        LocalDate endDayOfMonth = startDayOfMonth.plusMonths(1).minusDays(1);

        List<Transaction> currentMonthTransactions = byMyAccountId.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getTransactionTime().toLocalDate();
                    return transactionDate.isAfter(startDayOfMonth.minusDays(1)) && transactionDate.isBefore(endDayOfMonth.plusDays(1));
                }).collect(Collectors.toList());

        // 이번 달의 총 지출액을 구해주자
        // 그리고 카테고리별 지출내역을 더해주자
        Long currentMonthTotalExpenditure = (long)0;

        Map<Integer, Long> expenditureRatio = new HashMap<>();
        for (Transaction currentMonthTransaction : currentMonthTransactions) {
            currentMonthTotalExpenditure += currentMonthTransaction.getPaymentAmount();
            Integer businessCategory = currentMonthTransaction.getBusinessCategory();
            Long pay = currentMonthTransaction.getPaymentAmount();
            if(expenditureRatio.containsKey(businessCategory)) {
                Long formerPay = expenditureRatio.get(businessCategory);
                expenditureRatio.put(businessCategory, formerPay + pay);
            }
            else {
                expenditureRatio.put(businessCategory, pay);
            }
        }

        // 카테고리별 지출액의 비중
        for (Integer category : expenditureRatio.keySet()) {
            Long catagoryTotalPay = expenditureRatio.get(category);
            expenditureRatio.put(category, catagoryTotalPay / currentMonthTotalExpenditure);
        }
        
        // 저번 달 거래내역도 가져오자
        LocalDate startDayOfLastMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth().minus(1), 1);
        LocalDate endDayOfLastMonth = startDayOfLastMonth.plusMonths(1).minusDays(1);

        List<Transaction> lastMonthTransactions = byMyAccountId.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getTransactionTime().toLocalDate();
                    return transactionDate.isAfter(startDayOfLastMonth.minusDays(1)) && transactionDate.isBefore(endDayOfLastMonth.plusDays(1));
                }).collect(Collectors.toList());
        
        // 저번 달의 총 지출액을 구해주자
        Long lastMonthTotalExpenditure = 0L;
        for (Transaction lastMonthTransaction : lastMonthTransactions) {
            lastMonthTotalExpenditure += lastMonthTransaction.getPaymentAmount();
        }

        // 저번달 대비 이번달의 증감률을 구해주자
        Long growthRate = (currentMonthTotalExpenditure - lastMonthTotalExpenditure) / lastMonthTotalExpenditure * 100;

        return new MonthlyExpenditureDetailResponse(currentMonthTotalExpenditure, expenditureRatio, lastMonthTotalExpenditure, growthRate);
    }

    // 전체 기간에서 각 카테고리 별 지출 비율
    @Override
    public Map<Integer, Long> getCategoryExpenditureDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> byMyAccountId = transactionRepository.findByAccount(account);

        Map<Integer, Long> expenditureRatio = new HashMap<>();

        Long totalExpenditure = 0L;
        for (Transaction transaction : byMyAccountId) {

            Integer businessCategory = transaction.getBusinessCategory();
            Long pay = transaction.getPaymentAmount();
            totalExpenditure += pay;

            if(expenditureRatio.containsKey(businessCategory)) {
                Long formerPay = expenditureRatio.get(businessCategory);
                expenditureRatio.put(businessCategory, formerPay + pay);
            }
            else {
                expenditureRatio.put(businessCategory, pay);
            }
        }

        // 카테고리별 지출액의 비중
        for (Integer category : expenditureRatio.keySet()) {
            Long catagoryTotalPay = expenditureRatio.get(category);
            expenditureRatio.put(category, catagoryTotalPay / totalExpenditure);
        }

        return expenditureRatio;
    }

}
