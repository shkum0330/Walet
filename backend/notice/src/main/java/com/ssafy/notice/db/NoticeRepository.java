package com.ssafy.notice.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository  extends JpaRepository<NoticeEntity,Long> {
}
