package com.ssafy.notice.controller;

import com.ssafy.global.response.EnvelopeResponse;
import com.ssafy.notice.api.noticeDTO;
import com.ssafy.notice.db.NoticeEntity;
import com.ssafy.notice.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/list")
    public ResponseEntity<EnvelopeResponse<List<noticeDTO.request>>> getAllNotices() {
        List<NoticeEntity> notices = noticeService.getAllNotices();
        List<noticeDTO.request> noticeList = new ArrayList<>();

        for (NoticeEntity notice : notices) {
            noticeDTO.request data = new noticeDTO.request();
            BeanUtils.copyProperties(notice, data);
            noticeList.add(data);
        }

        EnvelopeResponse<List<noticeDTO.request>> response = new EnvelopeResponse<>(200, "데이터 처리 성공", noticeList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/create")
    public ResponseEntity<EnvelopeResponse<NoticeEntity>> createNotice(@RequestPart("data") noticeDTO.request request,
                                                                       @RequestPart("bannerImg") MultipartFile file) throws IOException {
        EnvelopeResponse<NoticeEntity> response = new EnvelopeResponse<>(201, "데이터 생성 성공", noticeService.createNotice(request, file));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<EnvelopeResponse<noticeDTO.request>> getNoticeByID(@PathVariable Long id) {

        NoticeEntity notice = noticeService.getNoticeByID(id);
        noticeDTO.request data = new noticeDTO.request();
        BeanUtils.copyProperties(notice, data);

        return ResponseEntity.status(HttpStatus.OK).body(new EnvelopeResponse<>(200, "데이터 처리 성공", data));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<EnvelopeResponse<noticeDTO.request>> updateNotice(@PathVariable Long id,
                                                                            @RequestPart("notice") noticeDTO.request request,
                                                                            @RequestPart("file") MultipartFile file) throws IOException {
        NoticeEntity updatedNotice = noticeService.updateNotice(id, request, file);

        noticeDTO.request data = new noticeDTO.request();
        BeanUtils.copyProperties(updatedNotice, data);

        return ResponseEntity.status(HttpStatus.OK).body(new EnvelopeResponse<>(200, "OK", data));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EnvelopeResponse<String>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new EnvelopeResponse<>(204, "데이터 삭제 성공", ""));
    }



    @PutMapping("/pop/{id}")
    public ResponseEntity<EnvelopeResponse<noticeDTO.request>> setActiveToTrue(@PathVariable Long id) {
        noticeService.setAllActiveToFalse();
        NoticeEntity notice = noticeService.setIsActiveToTrue(id);

        noticeDTO.request data = new noticeDTO.request();
        BeanUtils.copyProperties(notice, data);


        return ResponseEntity.status(HttpStatus.OK).body(new EnvelopeResponse<>(200, "데이터 처리 성공", data));
    }


    @GetMapping("/pop")
    public ResponseEntity<EnvelopeResponse<noticeDTO.request>> getPopBanner() {
        NoticeEntity notice = noticeService.getActiveNotice();

        noticeDTO.request data = new noticeDTO.request();
        BeanUtils.copyProperties(notice, data);


        return ResponseEntity.status(HttpStatus.OK).body(new EnvelopeResponse<>(200, "데이터 처리 성공", data));
    }

}