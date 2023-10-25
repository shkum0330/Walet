package com.ssafy.member.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    boolean existsByEmail(String email);
}
