package com.ssafy.notice.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByIsActiveTrue();
    NoticeEntity findFirstByIsActiveTrue();
}

