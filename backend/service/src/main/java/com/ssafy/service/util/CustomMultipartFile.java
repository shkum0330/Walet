package com.ssafy.service.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;


public class CustomMultipartFile implements MultipartFile {
    private final byte[] fileContent;
    private final String fileName;

    public CustomMultipartFile(File file) throws IOException {
        this.fileName = file.getName();
        this.fileContent = Files.readAllBytes(file.toPath());
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public boolean isEmpty() {
        return fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            fileOutputStream.write(fileContent);
        }
    }
}
