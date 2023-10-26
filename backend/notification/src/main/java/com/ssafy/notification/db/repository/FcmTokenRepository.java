package com.ssafy.notification.db.repository;

import com.ssafy.notification.db.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken,Long> {
    Optional<FcmToken> findByMemberId(Long memberId);
}
