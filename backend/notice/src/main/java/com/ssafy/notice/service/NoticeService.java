package com.ssafy.notice.service;


import com.ssafy.notice.db.NoticeEntity;
import com.ssafy.notice.api.Request;
import com.ssafy.notice.db.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository){
        this.noticeRepository = noticeRepository;
    }

    public NoticeEntity createNotice (Request.request request){
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setIsActive(request.getIsActive());
        return noticeRepository.save(notice);
    }

    public NoticeEntity getNoticeByID(Long id){
        return noticeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Notice found with id: " + id));
    }

    public NoticeEntity updateNotice(Long id, Request.request request) {
        NoticeEntity modifiedNotice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Notice found with id: " + id));

        modifiedNotice.setTitle(request.getTitle());
        modifiedNotice.setContent(request.getContent());
        modifiedNotice.setIsActive(request.getIsActive());

        return noticeRepository.save(modifiedNotice);
    }



    public void deleteNotice(Long id){
        noticeRepository.deleteById(id);
    }
}
