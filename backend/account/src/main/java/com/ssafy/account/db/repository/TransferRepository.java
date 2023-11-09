package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    List<Transfer> findByTransferorId(Long transferorId);

    void deleteByTransferorId(Long transferorId);
    Optional<Transfer> findByTransfereeId(Long transfereeId);
}
