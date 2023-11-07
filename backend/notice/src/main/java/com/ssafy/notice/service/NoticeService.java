package com.ssafy.notice.service;


import com.ssafy.notice.db.NoticeEntity;
import com.ssafy.notice.api.noticeDTO;
import com.ssafy.notice.db.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final S3Service s3UploadService;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, S3Service s3UploadService){
        this.noticeRepository = noticeRepository;
        this.s3UploadService = s3UploadService;
    }

    public NoticeEntity createNotice (noticeDTO.request request, MultipartFile file) throws IOException{
        String imageUrl = s3UploadService.saveFile(file);

        NoticeEntity notice = new NoticeEntity();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setIsActive(request.getIsActive());
        notice.setSubTitle(request.getSubTitle());
        notice.setBannerImg(imageUrl);

        return noticeRepository.save(notice);
    }

    public NoticeEntity getNoticeByID(Long id){
        return noticeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디가 없습니다."));
    }

    public NoticeEntity updateNotice(Long id, noticeDTO.request request, MultipartFile file) throws IOException {

        String imageUrl = s3UploadService.saveFile(file);
        NoticeEntity modifiedNotice = getNoticeByID(id);
        modifiedNotice.setTitle(request.getTitle());
        modifiedNotice.setContent(request.getContent());
        modifiedNotice.setIsActive(request.getIsActive());
        modifiedNotice.setSubTitle(request.getSubTitle());
        modifiedNotice.setBannerImg(imageUrl);
        return noticeRepository.save(modifiedNotice);
    }

    public NoticeEntity updateNoticetext(Long id, noticeDTO.request request) throws IOException {

        NoticeEntity modifiedNotice = getNoticeByID(id);
        modifiedNotice.setTitle(request.getTitle());
        modifiedNotice.setContent(request.getContent());
        modifiedNotice.setIsActive(request.getIsActive());
        modifiedNotice.setSubTitle(request.getSubTitle());
        return noticeRepository.save(modifiedNotice);
    }

    public void deleteNotice(Long id){
        noticeRepository.deleteById(id);
    }

    public List<NoticeEntity> getAllNotices() {
        return noticeRepository.findAll();
    }
    public void setAllActiveToFalse() {
        List<NoticeEntity> activeNotices = noticeRepository.findByIsActiveTrue();
        for (NoticeEntity notice : activeNotices) {
            notice.setIsActive(false);
        }
        noticeRepository.saveAll(activeNotices);
    }

    public NoticeEntity setIsActiveToTrue(Long id) {
        NoticeEntity notice = getNoticeByID(id);
        notice.setIsActive(true);
        return noticeRepository.save(notice);
    }

    public NoticeEntity getActiveNotice() {
        return noticeRepository.findFirstByIsActiveTrue();
    }

}
