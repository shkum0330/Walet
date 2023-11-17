package com.ssafy.service.service;

import com.ssafy.service.db.entity.Log;
import com.ssafy.service.db.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{
    private final LogRepository logRepository;

    public void saveLog(String url , String type , String date){
        Log log = Log.builder()
                .createdDay(date)
                .url(url)
                .type(type)
                .build();
        logRepository.save(log);
    }
}
