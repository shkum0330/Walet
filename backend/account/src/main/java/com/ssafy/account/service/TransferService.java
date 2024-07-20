package com.ssafy.account.service;

import com.ssafy.account.api.request.account.transfer.AccountTransferRequest;
import com.ssafy.account.db.entity.transfer.Transfer;

public interface TransferService {
    Long requestAccountTransfer(Long ownerId, AccountTransferRequest request);
    Transfer findByTransfereeId(Long transfereeId);
    Transfer findById(Long transferId);
}
