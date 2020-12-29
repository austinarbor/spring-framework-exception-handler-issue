package dev.aga.service;

import dev.aga.exception.UploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class UploadService {

    public void uploadFile(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            log.info("InputStream is open");
        } catch (IOException e) {
            throw new UploadException(e);
        }
    }
}
