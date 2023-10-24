package com.ssafy.notice.controller;

import com.ssafy.notice.api.noticeDTO;
import com.ssafy.notice.db.NoticeEntity;
import com.ssafy.notice.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/list")
    public List<noticeDTO.request> getAllNotices() {
        List<NoticeEntity> notices = noticeService.getAllNotices();
        List<noticeDTO.request> noticeList = new ArrayList<>();

        for (NoticeEntity notice : notices) {
            noticeDTO.request data = new noticeDTO.request();
            BeanUtils.copyProperties(notice, data);
            noticeList.add(data);
        }

        return noticeList;
    }

    @PostMapping("/create")
    public NoticeEntity createNotice(@RequestBody noticeDTO.request request) {
        return noticeService.createNotice(request);
    }

    @GetMapping("/{id}")
    public noticeDTO.noticeResponse getNoticeByID(@PathVariable Long id){
        try {
            NoticeEntity notice = noticeService.getNoticeByID(id);

            noticeDTO.request data = new noticeDTO.request();
            BeanUtils.copyProperties(notice, data);

            noticeDTO.noticeResponse response = new noticeDTO.noticeResponse();
            response.setMessage("OK");
            response.setData(data);

            return response;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Notice Found With This Id", e);
        }
    }


    @PutMapping("/{id}")
    public NoticeEntity updateNotice(@PathVariable Long id, @RequestBody noticeDTO.request request){
        return noticeService.updateNotice(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
    }


    @PutMapping("/{id}/pop")
    public NoticeEntity setActiveToTrue(@PathVariable Long id) {
        noticeService.setAllActiveToFalse();
        return noticeService.setIsActiveToTrue(id);
    }

}