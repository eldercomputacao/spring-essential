package com.elderpereira.springessential.app.rest.impl;

import com.elderpereira.springessential.app.rest.FileController;
import com.elderpereira.springessential.infra.files.FileStorageService;
import com.elderpereira.springessential.app.response.UploadFileResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class FileRestController implements FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) {
        var filename = fileStorageService.storeFile(file);
        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download-file/")
                .path(filename)
                .toUriString();

        return UploadFileResponse.builder()
                .fileName(filename)
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
    }

    @Override
    public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files) {
        return Arrays.stream(files).map(this::uploadFile).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String filename, HttpServletRequest request) {

        Resource resource = fileStorageService.loadFileAsResource(filename);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            log.info("Could not determine file type!");
        }

        if (contentType.isBlank()) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
