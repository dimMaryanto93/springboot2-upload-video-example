package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.dao.UploadMediaDao;
import com.maryanto.dimas.example.entity.UploadMedia;
import com.maryanto.dimas.example.utils.FileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class UploadMediaService {

    @Autowired
    private UploadMediaDao dao;

    @Autowired
    private FileStorageUtil storageUtil;

    @Transactional
    public UploadMedia createFile(MultipartFile file) throws IOException {
        UploadMedia media = new UploadMedia();
        media.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        media.setWrite(false);
        media.setCompressed(false);
        String path = storageUtil.createFile(file);
        media.setOriginalFilePath(path);
        return dao.upload(media);
    }

    @Transactional
    public void compressed(UploadMedia media){
        dao.convert(media);
    }


}
