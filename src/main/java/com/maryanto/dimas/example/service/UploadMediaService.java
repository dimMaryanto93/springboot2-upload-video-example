package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.dao.UploadMediaDao;
import com.maryanto.dimas.example.entity.UploadMedia;
import com.maryanto.dimas.example.utils.FileStorageUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UploadMediaService {

    @Autowired
    private UploadMediaDao dao;

    @Autowired
    private FileStorageUtil storageUtil;

    public byte[] getFile(String path) throws IOException {
        return storageUtil.getFile(path);
    }

    public Optional<UploadMedia> findById(String id) {
        return dao.findById(id);
    }

    @Transactional
    public UploadMedia createFile(MultipartFile file) throws IOException {
        UploadMedia media = new UploadMedia();
        media.setOriginalFileName(Paths.get(file.getOriginalFilename()).getFileName().toString());
        media.setOriginalFileExtension(FilenameUtils.getExtension(media.getOriginalFileName()));
        media.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        media.setWrite(false);
        media.setCompressed(false);
        String path = storageUtil.createFile(file);
        media.setOriginalFilePath(path);
        return dao.upload(media);
    }

    @Transactional
    public void compressed(UploadMedia media) {
        dao.convert(media);
    }

    @Transactional
    public void thumbnail(UploadMedia media) {
        dao.generateThumbnail(media);
    }

}
