package com.ssafy.account.service;

public interface TransferService {
    Long requestAccountTransfer(Long ownerId,String accountNumber);
}
