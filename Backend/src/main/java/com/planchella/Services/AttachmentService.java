package com.planchella.Services;

import com.planchella.domain.AttachmentMetadata;
import com.planchella.enums.MimeType;
import com.planchella.mappers.AttachmentMapper;
import com.planchella.repositories.attachments.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;
    Map<String, Date> generatedIds;
    Map<String, Date> acknowledged;

    public AttachmentService() {
        this.generatedIds = new ConcurrentHashMap<>();
        this.acknowledged = new ConcurrentHashMap<>();
    }

    @Scheduled(cron = "0 */10 * * * *")
    private void cleanupIdTracker() {
        for (Map.Entry<String, Date> entry : this.generatedIds.entrySet()) {
            if (entry.getValue().after(new Date()))
                continue;
            this.generatedIds.remove(entry.getKey());
        }
    }

    @Scheduled(cron = "0 */15 * * * *")
    private void cleanupAcknowledgement() {
        for (Map.Entry<String, Date> entry : this.generatedIds.entrySet()) {
            if (entry.getValue().after(new Date()))
                continue;
            this.generatedIds.remove(entry.getKey());
        }
    }

    public AttachmentMetadata saveAttachment(String id, MimeType mimeType, String fileName, InputStream inputStream) {
        if (!isValidAttachmentId(id))
            return null;

        generatedIds.remove(id);
        long expirationTime = (long) (new Date().getTime() + 60 * 1e3 * 5);
        this.acknowledged.put(id, new Date(expirationTime));

        AttachmentMetadata attachmentMetadata = new AttachmentMetadata();
        attachmentMetadata.setId(id);
        attachmentMetadata.setFileName(fileName);
        attachmentMetadata.setMimeType(mimeType);
        attachmentMetadata.setSize(attachmentRepository.saveAttachment(id, inputStream));

        attachmentRepository.saveAttachmentMetadata(AttachmentMapper.domainToEntity(attachmentMetadata));
        return attachmentMetadata;
    }

    public AttachmentMetadata saveAttachment(MimeType mimeType, String fileName, InputStream inputStream) {
        AttachmentMetadata attachmentMetadata = new AttachmentMetadata();
        String id = this.generateAttachmentId(false);
        attachmentMetadata.setId(id);
        attachmentMetadata.setFileName(fileName);
        attachmentMetadata.setMimeType(mimeType);
        long size = attachmentRepository.saveAttachment(id, inputStream);
        attachmentMetadata.setSize(size);

        if (size > 0) {
            long expirationTime = (long) (new Date().getTime() + 60 * 1e3 * 5);
            this.acknowledged.put(id, new Date(expirationTime));
        }
        attachmentRepository.saveAttachmentMetadata(AttachmentMapper.domainToEntity(attachmentMetadata));
        return attachmentMetadata;
    }

    public InputStream getAttachmentStream(String attachment_id) {
        return attachmentRepository.getAttachmentStream(attachment_id);
    }

    public AttachmentMetadata getAttachmentMetadata(String attachment_id) {
        return AttachmentMapper.entityToDomain(attachmentRepository.getAttachmentMetadata(attachment_id));
    }

    public String generateAttachmentId(boolean track_id) {
        String uuid = UUID.randomUUID().toString();
        if (track_id) {
            long expirationTime = (long) (new Date().getTime() + 60 * 1e3 * 5);
            generatedIds.put(uuid, new Date(expirationTime));
        }
        return uuid;
    }

    public boolean isValidAttachmentId(String id) {
        return generatedIds.containsKey(id) && (new Date().before(generatedIds.get(id)));
    }

    public boolean isAcknowledged(String attachment_id) {
        return acknowledged.containsKey(attachment_id) || attachmentRepository.attachmentExists(attachment_id);
    }
}
