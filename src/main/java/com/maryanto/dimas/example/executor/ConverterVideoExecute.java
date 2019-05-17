package com.maryanto.dimas.example.executor;

import com.maryanto.dimas.example.entity.UploadMedia;
import com.maryanto.dimas.example.service.UploadMediaService;
import com.maryanto.dimas.example.utils.CompressedMediaVideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class ConverterVideoExecute {

    @Autowired
    private CompressedMediaVideoUtil compress;

    @Autowired
    private UploadMediaService service;

    public void execute(Map<String, Object> value) {
        UploadMedia media = new UploadMedia();
        media.setId(value.get("id").toString());
        media.setOriginalFilePath(value.get("original_path").toString());
        try {
            String compressedPath = compress.convertVideo(media.getOriginalFilePath(), "mp4");
            media.setCompressedFilePath(compressedPath);
            media.setCompressed(true);
            service.compressed(media);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
