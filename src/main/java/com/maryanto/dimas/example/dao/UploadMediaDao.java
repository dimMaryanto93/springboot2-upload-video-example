package com.maryanto.dimas.example.dao;

import com.maryanto.dimas.example.entity.UploadMedia;
import com.maryanto.dimas.example.repository.UploadMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UploadMediaDao {

    @Autowired
    private UploadMediaRepository repository;

    public UploadMedia upload(UploadMedia value) {
        return repository.save(value);
    }

    public void write(UploadMedia value) {
        repository.uploadFileSuccess(value.getId(), value.getOriginalFilePath());
    }

    public void convert(UploadMedia value) {
        repository.convertFileSuccess(value.getId(), value.getCompressedFilePath());
    }
}
