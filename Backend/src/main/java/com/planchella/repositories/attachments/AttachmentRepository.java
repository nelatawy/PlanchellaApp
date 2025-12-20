package com.planchella.repositories.attachments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planchella.domain.AttachmentMetadata;
import com.planchella.encryption.EncryptedInputStream;
import com.planchella.encryption.EncryptedOutputStream;
import com.planchella.entities.AttachmentEntity;
import com.planchella.enums.MimeType;
import com.planchella.mappers.AttachmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Repository
public class AttachmentRepository {
    @Value("${mail.attachment-root:data/attachment}")
    private String attachmentRoot;

    @Autowired
    private ObjectMapper objectMapper;

    SessionFactory sessionFactory;

    public AttachmentRepository() {
        this.sessionFactory = com.planchella.Configs.HibernateUtil.getSessionFactory();
    }

    public Long saveAttachment(String id, InputStream in) {
        long size = 0L;
        try {
            Files.createDirectories(Paths.get(attachmentRoot));
            Path dir = Path.of(attachmentRoot, id + "." + "bin");
            try (OutputStream out = new EncryptedOutputStream(new FileOutputStream(dir.toFile()))) {
                size = in.transferTo(out);
            }
        } catch (Exception e) {
            System.err.println("Error saving attachment to file" + e.getMessage());
        }
        return size;
    }

    public InputStream getAttachmentStream(String attachmentId) {

        try {
            Path path = Path.of(attachmentRoot, attachmentId + "." + "bin");
            File src = path.toFile();
            if (src.exists()) {
                return new EncryptedInputStream(new FileInputStream(src));
            } else {
                String ext = MimeType.toFileExtension(
                        getAttachmentMetadata(attachmentId).getMimeType());
                path = Path.of(attachmentRoot, attachmentId + "." + ext);
                return new FileInputStream(path.toFile());
            }
        } catch (Exception e) {
            System.err.println("Error getting attachment stream" + e.getMessage());
        }
        return null;
    }

    /**
     * Support for File-System based metadata storing
     */

    // public void saveAttachmentMetadata(AttachmentMetadata data){
    // try {
    // Path path = Path.of(attachmentRoot, data.getId() + ".json");
    // String strData = objectMapper.writeValueAsString(data);
    // Files.writeString(path, strData, StandardCharsets.UTF_8);
    // }catch(Exception e){
    // System.err.println("Error saving attachment metadata to file" +
    // e.getMessage());
    // }
    //
    // }
    //
    // public AttachmentMetadata getAttachmentMetadata(String attachmentId){
    // Path dir = Path.of(attachmentRoot, attachmentId + ".json");
    // try {
    // return objectMapper.readValue(dir.toFile(), AttachmentMetadata.class);
    // } catch (Exception e) {
    // System.err.println("Error getting attachment metadata" + e.getMessage());
    // }
    // return null;
    // }

    //
    // public boolean attachmentExists(String attachmentId) {
    // try {
    // Path path = Paths.get(attachmentRoot, attachmentId + ".json");
    // return path.toFile().exists();
    // } catch (Exception e) {
    // System.err.println("Error getting attachment stream" + e.getMessage());
    // return false;
    // }
    //
    // }

    /**
     * Support for DB-based metadata storing
     * 
     * @param data the attachment's metadata
     */
    public void saveAttachmentMetadata(AttachmentEntity data) {
        try {
            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            if (session.get(AttachmentEntity.class, data.getId()) == null) {
                session.persist(data);
            } else {
                session.merge(data);
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            System.err.println("Error while trying to save the attachment metadata" + e.getMessage());
            return;
        }

    }

    /**
     * retrieves the attachment metadata
     * 
     * @param attachmentId the id of the required attachment
     * @return the attachment entity
     */

    public AttachmentEntity getAttachmentMetadata(String attachmentId) {
        try {
            Session session = sessionFactory.openSession();
            AttachmentEntity attachmentEntity = session.get(AttachmentEntity.class,
                    attachmentId);
            session.close();
            return attachmentEntity;
        } catch (Exception e) {
            System.err.println("Error while trying to find the attachment");
            return null;
        }
    }

    /**
     * checks whether the attachment exist
     * 
     * @param attachmentId the id of the required attachment
     * @return a boolean indicating whether the attachment exists
     */

    public boolean attachmentExists(String attachmentId) {
        try {
            Session session = sessionFactory.openSession();
            AttachmentEntity attachmentEntity = session.get(AttachmentEntity.class, attachmentId);
            boolean exists = (attachmentEntity != null);
            session.close();
            return exists;
        } catch (Exception e) {
            System.err.println("Error while trying to find the attachment");
            return false;
        }

    }

}
