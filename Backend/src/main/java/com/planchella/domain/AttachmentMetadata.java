package com.planchella.domain;

import com.planchella.enums.MimeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "attachments")
public class AttachmentMetadata {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "mime_type")
    private MimeType mimeType;

    @Column(name = "size")
    private long size;

    public AttachmentMetadata(String id, String fileName, MimeType mimeType, long size) {
        this.id = id;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.size = size;
    }

    public AttachmentMetadata() {
    }

}
