package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.access.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {
    List<Access> findByAccountNumberAndIsConfirmed(String accountNumber, int isConfirmed);
    List<Access> findByRequestMemberIdAndIsConfirmed(Long requestMemberId, int isConfirmed);

    Access findAccessByRequestMemberIdAndAccountNumberAndIsConfirmed(Long requestMemberId, String accountNumber, int isConfirmed);

    Access findByRequestMemberIdAndAccountNumber(Long requestMemberId, String accountNumber);
}
