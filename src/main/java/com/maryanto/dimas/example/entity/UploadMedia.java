package com.maryanto.dimas.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
public class UploadMedia {

    @Id
    @GenericGenerator(name = "uuid_gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid_gen")
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @JsonIgnore
    @Column(name = "original_path")
    @Type(type = "text")
    public String originalFilePath;

    @Column(name = "original_file_name", nullable = false)
    @Type(type = "text")
    public String originalFileName;

    @Column(name = "original_file_ext", nullable = false)
    public String originalFileExtension;

    @JsonIgnore
    @Column(name = "is_write")
    private boolean write;

    @JsonIgnore
    @Column(name = "compressed_path")
    @Type(type = "text")
    public String compressedFilePath;

    @JsonIgnore
    @Column(name = "is_compressed")
    private boolean compressed;

    @JsonIgnore
    @Column(name = "thumbnail_path")
    @Type(type = "text")
    private String thumbnailPath;

    @JsonIgnore
    @Column(name = "is_thumbnail")
    private boolean thumbnail;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

}
