package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    List<Transfer> findByTransferorId(Long transferorId);
    @Transactional
    void deleteByTransferorId(Long transferorId);
}
