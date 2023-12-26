package com.elderpereira.springessential.app.rest;

import com.elderpereira.springessential.app.response.UploadFileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("files")
@Tag(name = "File Endpoints")
public interface FileController {
    @PostMapping("upload-file")
    UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file);

    @PostMapping("upload-multiple-files")
    List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile [] files);

    @GetMapping("/download-file/{filename:.+}")
    ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request);
}
