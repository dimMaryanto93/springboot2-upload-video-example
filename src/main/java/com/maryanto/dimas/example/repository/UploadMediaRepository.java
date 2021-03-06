package com.maryanto.dimas.example.repository;

import com.maryanto.dimas.example.entity.UploadMedia;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UploadMediaRepository extends CrudRepository<UploadMedia, String> {

    @Modifying
    @Query("update UploadMedia set compressedFilePath = ?2, compressed = true where id = ?1")
    int convertFileSuccess(String id, String path);

    @Modifying
    @Query("update UploadMedia set thumbnailPath = ?2, thumbnail = true where id = ?1")
    int generateThumbnail(String id, String thumbnailPath);
}
