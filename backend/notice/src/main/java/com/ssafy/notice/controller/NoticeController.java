package com.ssafy.notice.controller;

import com.ssafy.notice.api.Request;
import com.ssafy.notice.api.Response;
import com.ssafy.notice.db.NoticeEntity;
import com.ssafy.notice.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/")
    public NoticeEntity createNotice(@RequestBody Request.request request) {
        return noticeService.createNotice(request);
    }

    @GetMapping("/{id}")
    public Response.noticeResponse getNoticeByID(@PathVariable Long id){
        try {
            NoticeEntity notice = noticeService.getNoticeByID(id);

            Request.request data = new Request.request();
            BeanUtils.copyProperties(notice, data);

            Response.noticeResponse response = new Response.noticeResponse();
            response.setMessage("OK");
            response.setData(data);

            return response;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Notice Found With This Id", e);
        }
    }



    @PutMapping("/{id}")
    public NoticeEntity updateNotice(@PathVariable Long id, @RequestBody Request.request request){
        return noticeService.updateNotice(id, request);
    }


    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable Long id){
        noticeService.deleteNotice(id);
    }
}