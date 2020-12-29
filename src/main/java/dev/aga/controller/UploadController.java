package dev.aga.controller;

import dev.aga.exception.UploadException;
import dev.aga.model.ErrorDetails;
import dev.aga.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UploadController {
    private final UploadService service;

    @PostMapping("upload")
    public Map<String, Object> upload(@RequestParam("file") List<MultipartFile> files) {
        Map<String, Object> m = new HashMap<>();
        for (MultipartFile file : files) {
            service.uploadFile(file);
            m.put(file.getOriginalFilename(), "success");
        }
        return m;
    }

    @ExceptionHandler(UploadException.class)
    ResponseEntity<ErrorDetails> handleUploadException(UploadException e, HttpServletRequest request) {
        log.info("Inside handleUploadException");
        var errorDetails = new ErrorDetails(request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
