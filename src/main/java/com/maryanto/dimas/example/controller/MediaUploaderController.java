package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.entity.UploadMedia;
import com.maryanto.dimas.example.service.UploadMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/media")
public class MediaUploaderController {

    @Value("${server.compression.mime-types}")
    private List<String> contentVideos;

    @Autowired
    private UploadMediaService service;

    @PostMapping(
            value = "/upload/video",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE}
    )
    public ResponseEntity<?> createVideo(
            @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (!contentVideos.contains(contentType)) {
            log.info("ini bukan video dengan type: {}", contentType);
            return ResponseEntity.badRequest().body("File bukan video!");
        }

        UploadMedia metaData = service.createFile(file);
        return ResponseEntity.ok(metaData);
    }

}
