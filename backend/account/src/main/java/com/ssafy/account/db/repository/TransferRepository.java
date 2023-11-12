package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ssafy.account.db.entity.transfer.Transfer.*;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    List<Transfer> findByTransferorId(Long transferorId);

    void deleteByTransferorId(Long transferorId);
    void deleteByStatusAndCreatedAtLessThanEqual(TransferStatus status,LocalDateTime createdAt);
    Optional<Transfer> findByTransfereeId(Long transfereeId);

    List<Transfer> findByStatus(TransferStatus status);
}
