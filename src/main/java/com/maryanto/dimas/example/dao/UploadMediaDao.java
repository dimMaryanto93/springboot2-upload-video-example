package com.maryanto.dimas.example.dao;

import com.maryanto.dimas.example.entity.UploadMedia;
import com.maryanto.dimas.example.repository.UploadMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UploadMediaDao {

    @Autowired
    private UploadMediaRepository repository;

    public Optional<UploadMedia> findById(String id) {
        return repository.findById(id);
    }

    public UploadMedia upload(UploadMedia value) {
        return repository.save(value);
    }

    public void convert(UploadMedia value) {
        repository.convertFileSuccess(value.getId(), value.getCompressedFilePath());
    }

    public void generateThumbnail(UploadMedia media) {
        repository.generateThumbnail(media.getId(), media.getThumbnailPath());
    }
}
