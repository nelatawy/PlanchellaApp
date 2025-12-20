package com.planchella.entities;

import com.planchella.enums.MimeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attachments")
@Getter
@Setter
public class AttachmentEntity {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @Column(name = "file_name")
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mime_type")
    private MimeType mimeType;

    @Column(name = "size")
    private long size;

    public AttachmentEntity(String id, String fileName, MimeType mimeType, long size) {
        this.id = id;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.size = size;
    }

    public AttachmentEntity() {
    }
}
