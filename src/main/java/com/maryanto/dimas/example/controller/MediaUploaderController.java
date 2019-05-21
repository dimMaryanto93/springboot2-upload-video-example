package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.entity.UploadMedia;
import com.maryanto.dimas.example.service.UploadMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

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
            return badRequest().body("File bukan video!");
        }

        UploadMedia metaData = service.createFile(file);
        return ok(metaData);
    }

    @GetMapping("/download/{id}/thumbnail")
    public ResponseEntity<?> getThumbnailById(@PathVariable("id") String id) {
        Optional<UploadMedia> mediaOptional = service.findById(id);
        if (!mediaOptional.isPresent()) {
            return noContent().build();
        }

        UploadMedia media = mediaOptional.get();
        if (!media.isThumbnail()) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }

        String path = media.getThumbnailPath();
        try {
            byte[] file = service.getFile(path);
            String encodeString = Base64.getEncoder().encodeToString(file);
            return ok(encodeString);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
