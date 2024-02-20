package com.ssafy.member.db;

import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
    public List<Member> findByRole(Role role);
    List<Member> findByNameContaining(String keyword);

    List<Member> findByCreatedDateAfter(LocalDateTime startDate);


}
