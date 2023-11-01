package com.example.account.db.repository;

import com.example.account.db.entity.access.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {
    List<Access> findAccessesByAccountNumberAndIsConfirmed(String accountNumber, int isConfirmed);
    List<Access> findAccessesByRequestMemberIdAndIsConfirmed(Long requestMemberId, int isConfirmed);
}
