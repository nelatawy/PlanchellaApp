package com.planchella.domain;

import com.planchella.enums.MimeType;
import lombok.Data;

@Data
public class AttachmentMetadata {
    private String id;
    private String fileName;
    private MimeType mimeType;
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
