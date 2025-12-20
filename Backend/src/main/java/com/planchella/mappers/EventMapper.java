package com.planchella.mappers;

import com.planchella.DTOs.EventDTO;
import com.planchella.domain.Event;
import com.planchella.entities.AttachmentEntity;
import com.planchella.entities.CommunityEntity;
import com.planchella.entities.EventEntity;
import com.planchella.entities.UserEntity;
import org.hibernate.Session;

import java.util.stream.Collectors;

public class EventMapper {

    public static EventEntity domainToEntity(Event event, Session session) {
        EventEntity e = new EventEntity();

        e.setId(event.getId());
        e.setDescription(event.getDescription());
        e.setAuthor(session.getReference(UserEntity.class, event.getAuthor_id()));
        e.setCommunity(session.getReference(CommunityEntity.class, event.getCommunity_id()));
        e.setTitle(event.getTitle());
        e.setCreationDate(event.getCreationDate());
        e.setEventSize(event.getEventSize());
        e.setEventType(event.getEventType());

        if (event.getAttachments() != null) {
            e.setAttachments(event.getAttachments().stream()
                    .map(att -> {
                        // Use getReference to avoid loading the full attachment
                        // and to tell Hibernate this record already exists.
                        AttachmentEntity entity = session.getReference(AttachmentEntity.class, att.getId());
                        entity.setEvent(e); // Set the back-reference (owning side)
                        return entity;
                    })
                    .collect(Collectors.toList()));
        }

        return e;
    }

    public static Event entityToDomain(EventEntity e) {
        return new Event(e.getId(),
                e.getEventType(),
                e.getEventSize(),
                e.getAuthor().getId(),
                e.getCommunity().getId(),
                e.getTitle(),
                e.getDescription(),
                e.getCreationDate(),
                e.getAttachments() == null ? null
                        : e.getAttachments().stream()
                                .map(AttachmentMapper::entityToDomain)
                                .collect(Collectors.toList()));
    }

    public static EventDTO domainToDTO(Event event) {
        return new EventDTO(event.getId(),
                event.getEventType(),
                event.getEventSize(),
                event.getAuthor_id(),
                event.getCommunity_id(),
                event.getTitle(),
                event.getDescription(),
                event.getCreationDate(),
                event.getAttachments());
    }

    public static Event DTOtoDomain(EventDTO e) {
        return new Event(e.id,
                e.eventType,
                e.eventSize,
                e.authorId,
                e.communityId,
                e.title,
                e.description,
                e.creationDate,
                e.attachments);
    }
}
