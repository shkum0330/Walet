package com.example.account.service.impl;

import com.example.account.api.request.*;
import com.example.account.api.response.AnimalAccountDetailResponse;
import com.example.account.api.response.GeneralAccountDetailResponse;
import com.example.account.api.response.MonthlyExpenditureDetailResponse;
import com.example.account.api.response.TransactionResponse;
import com.example.account.common.api.exception.NotFoundException;
import com.example.account.db.entity.Account;
import com.example.account.db.entity.AccountState;
import com.example.account.db.entity.Transaction;
import com.example.account.db.repository.AccountRepository;
import com.example.account.db.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.account.common.api.status.FailCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // 일반계좌 상세정보
    public GeneralAccountDetailResponse getGeneralAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 최근 5개의 거래내역을 가져옴
        List<Transaction> transactionHistory = account.getTransactionHistory();
        Collections.sort(transactionHistory, (transaction1, transaction2)
                -> transaction2.getCreatedAt().compareTo(transaction1.getCreatedAt()));
        List<Transaction> recentTransactions = transactionHistory.subList(0, Math.min(5, transactionHistory.size()));

        GeneralAccountDetailResponse result = new GeneralAccountDetailResponse(account, recentTransactions);
        return result;
    }

    // 반려동물계좌 상세정보
    public AnimalAccountDetailResponse getAnimalAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 최근 5개의 거래내역을 가져옴
        List<Transaction> transactionHistory = account.getTransactionHistory();
        Collections.sort(transactionHistory, (transaction1, transaction2)
                -> transaction2.getCreatedAt().compareTo(transaction1.getCreatedAt()));
        List<Transaction> recentTransactions = transactionHistory.subList(0, Math.min(5, transactionHistory.size()));

        AnimalAccountDetailResponse result = new AnimalAccountDetailResponse(account, recentTransactions);
        return result;
    }

    // 반려동물 용품 구입 관련 거래내역 추가
    public Long addTransaction(TransactionRequest transactionRequest) {
        Long myAccountId = transactionRequest.getMyAccountId();
        Long companyAccountId = transactionRequest.getCompanyAccountId();

        Account myAccount = accountRepository.findById(myAccountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        Account companyAccount = accountRepository.findById(companyAccountId).orElseThrow(() -> new NotFoundException(NO_COMPANY_ACCOUNT));

        // 사용 가능 계좌인지 확인
        if(myAccount.getState() == AccountState.CLOSED || myAccount.getState() == AccountState.LOCKED || myAccount.getState() == AccountState.SUSPENDED) {
            throw new NotFoundException(NOT_USABLE_ACCOUNT);
        }
        if(companyAccount.getState() == AccountState.CLOSED || companyAccount.getState() == AccountState.LOCKED || myAccount.getState() == AccountState.SUSPENDED) {
            throw new NotFoundException(NOT_USABLE_COMPANY_ACCOUNT);
        }

        Long pay = transactionRequest.getPay();
        myAccount.minusBalance(pay);
        companyAccount.addBalance(pay);

        Transaction transaction = new Transaction(myAccount, companyAccount.getDepositorName(), companyAccount.getBusinessType(), transactionRequest.getTransactionType(), pay, myAccount.getBalance());
        myAccount.addTransaction(transaction);
        transactionRepository.save(transaction);
        return transaction.getId();
    }

    // 거래내역 조회
    public List<TransactionResponse> getTransactionHistory(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> transactions = account.getTransactionHistory();
        List<TransactionResponse> result = transactions.stream().map(transaction -> new TransactionResponse(transaction)).collect(Collectors.toList());
        return result;
    }
    
    // 설정한 기간 내의 거래내역 조회
    public List<TransactionResponse> getSpecificPeriodTransaction(TransactionPeriodRequest request) {
        Account account = accountRepository.findById(request.getAccountId()).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> allTransaction = account.getTransactionHistory();

        List<Transaction> transactionsInPeriod = new ArrayList<>();
        for (Transaction transaction : allTransaction) {
            LocalDateTime transactionDateTime = transaction.getCreatedAt();
            LocalDate transactionDate = transactionDateTime.toLocalDate();

            if(transactionDate.isAfter(request.getStart()) && transactionDate.isBefore(request.getEnd())) {
                transactionsInPeriod.add(transaction);
            }
        }

        // 최신 거래부터 보여줌
        Collections.sort(transactionsInPeriod, (transaction1, transaction2)
                -> transaction2.getCreatedAt().compareTo(transaction1.getCreatedAt()));
        List<TransactionResponse> result = transactionsInPeriod.stream().map((transaction ->
                        new TransactionResponse(transaction)))
                .collect(Collectors.toList());
        return result;
    }

    // 일반계좌 발급
    public Long registGeneralAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest);

        // 사업자계좌면(accountType이 false라면) 사업유형도 입력
        if(!accountRequest.isAccountType()) {
            account.addBusinessType(accountRequest.getBusinessType());
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
        account.createAccountNumber(accountNumber);
        
        // 계좌 정보를 DB에 저장
        accountRepository.save(account);
        return account.getId();
    }

    // 동물계좌 발급
    // 동물계좌 생성 버튼 클릭 -> 모계좌를 연결할 사람은 기존에 있던 농협 계좌 중 선택(필수사항x) -> 내 반려동물 정보 입력 -> 동물계좌 완성
    // cf) 계좌를 등록하려고 할 때 내가 등록할 비문 사진이 이미 있으면 양도 받는 것이 목적이냐고 물어보기
    public Long registAnimalAccount(AnimalAccountSaveRequest animalAccountRequest) {

        Account animalAccount = new Account(animalAccountRequest);
        
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

    // 동물계좌 양도
    // 상대방 반려동물 계좌 생성 -> 이전 주인의 반려동물 계좌에서 돈 전부 이체 -> 이전 주인의 반려동물 계좌 삭제(폐쇄 상태로 변경)
    // 잔액을 양도 받은 계좌의 잔액 반환
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
    public void updateStateToActive(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToActive();
    }

    // 2. 잠금
    public void updateStateToLocked(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToLocked();
    }

    // 3. 정지
    public void updateStateToSuspended(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToSuspended();
    }

    // 4. 폐쇄
    public void updateStateToClosed(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        account.updateStateToClosed();
    }

    // 이번 달 동물계좌 지출내역 표시(제한된 카테고리만)
    // 이전 달 총 지출액도 함께 보내줌
    public MonthlyExpenditureDetailResponse getMonthlyExpenditureDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> byMyAccountId = transactionRepository.findByMyAccountId(accountId);

        LocalDate currentDate = LocalDate.now();

        // 이번 달 거래내역을 가져오자
        LocalDate startDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
        LocalDate endDayOfMonth = startDayOfMonth.plusMonths(1).minusDays(1);

        List<Transaction> currentMonthTransactions = byMyAccountId.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getCreatedAt().toLocalDate();
                    return transactionDate.isAfter(startDayOfMonth.minusDays(1)) && transactionDate.isBefore(endDayOfMonth.plusDays(1));
                }).collect(Collectors.toList());

        // 이번 달의 총 지출액을 구해주자
        // 그리고 카테고리별 지출내역을 더해주자
        Long currentMonthTotalExpenditure = (long)0;

        Map<Integer, Long> expenditureRatio = new HashMap<>();
        for (Transaction currentMonthTransaction : currentMonthTransactions) {
            currentMonthTotalExpenditure += currentMonthTransaction.getPay();
            Integer businessCategory = currentMonthTransaction.getBusinessCategory();
            Long pay = currentMonthTransaction.getPay();
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
                    LocalDate transactionDate = transaction.getCreatedAt().toLocalDate();
                    return transactionDate.isAfter(startDayOfLastMonth.minusDays(1)) && transactionDate.isBefore(endDayOfLastMonth.plusDays(1));
                }).collect(Collectors.toList());
        
        // 저번 달의 총 지출액을 구해주자
        Long lastMonthTotalExpenditure = (long)0;
        for (Transaction lastMonthTransaction : lastMonthTransactions) {
            lastMonthTotalExpenditure += lastMonthTransaction.getPay();
        }

        // 저번달 대비 이번달의 증감률을 구해주자
        Long growthRate = (currentMonthTotalExpenditure - lastMonthTotalExpenditure) / lastMonthTotalExpenditure * 100;

        return new MonthlyExpenditureDetailResponse(currentMonthTotalExpenditure, expenditureRatio, lastMonthTotalExpenditure, growthRate);
    }

    // 전체 기간에서 각 카테고리 별 지출 비율
    public Map<Integer, Long> getCategoryExpenditureDetail(Long accountId) {
        List<Transaction> byMyAccountId = transactionRepository.findByMyAccountId(accountId);

        Map<Integer, Long> expenditureRatio = new HashMap<>();

        Long totalExpenditure = (long)0;
        for (Transaction transaction : byMyAccountId) {

            Integer businessCategory = transaction.getBusinessCategory();
            Long pay = transaction.getPay();
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

    // 추가적으로 제공되는 농협 api
    // 1. 예금주 조회
    // 2. 예금주 실명확인
}
