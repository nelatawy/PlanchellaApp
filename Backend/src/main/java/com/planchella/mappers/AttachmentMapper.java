package com.planchella.mappers;

import com.planchella.domain.AttachmentMetadata;
import com.planchella.entities.AttachmentEntity;

public class AttachmentMapper {

    public static AttachmentEntity domainToEntity(AttachmentMetadata domain) {
        if (domain == null)
            return null;
        AttachmentEntity entity = new AttachmentEntity();
        entity.setId(domain.getId());
        entity.setFileName(domain.getFileName());
        entity.setMimeType(domain.getMimeType());
        entity.setSize(domain.getSize());
        return entity;
    }

    public static AttachmentMetadata entityToDomain(AttachmentEntity entity) {
        if (entity == null)
            return null;
        AttachmentMetadata domain = new AttachmentMetadata();
        domain.setId(entity.getId());
        domain.setFileName(entity.getFileName());
        domain.setMimeType(entity.getMimeType());
        domain.setSize(entity.getSize());
        return domain;
    }
}
