package com.ssafy.service.util;

import com.ssafy.service.service.LogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class S3SaveService {
    private final S3Service s3Service;
    private final LogServiceImpl logService;

    @Scheduled(cron = "0 5 0 * * *") // 매일 정각 5분에 실행되는 크론 표현식
//    @Scheduled(initialDelay = 1000 , fixedDelay = 100000000)
    private void uploadLogToS3() {
        LocalDate day = LocalDate.now().minusDays(1);
        String errorFileName = "error-" + day + ".log";
        String infoFileName = "info-" + day + ".log";
        File errorFile = new File("./log/error/" + errorFileName);
        File infoFile = new File("./log/info/" + infoFileName);
        MultipartFile savefile = null;
        if(infoFile.exists()){
            try {
                savefile = new CustomMultipartFile(infoFile);
                logService.saveLog(s3Service.saveFile(savefile), "info ", day.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(errorFile.exists()){
            try {
                savefile = new CustomMultipartFile(infoFile);
                logService.saveLog(s3Service.saveFile(savefile), "error ", day.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
