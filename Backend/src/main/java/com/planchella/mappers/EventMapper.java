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
        e.setAuthor(session.getReference(UserEntity.class, event.getAuthorId()));
        e.setCommunity(session.getReference(CommunityEntity.class, event.getCommunityId()));
        e.setTitle(event.getTitle());
        e.setCreationDate(event.getCreationDate());
        e.setExpirationTime(event.getExpirationDate());
        e.setEventSize(event.getEventSize());
        e.setEventType(event.getEventType());
        e.setUpvoteCount(event.getUpvoteCount());
        e.setDownvoteCount(event.getDownvoteCount());
        e.setEventStartDate(event.getEventStartDate());
        e.setEventEndDate(event.getEventEndDate());

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
                e.getExpirationTime(),
                e.getUpvoteCount(),
                e.getDownvoteCount(),
                e.isHasTime(),
                e.getEventStartDate(),
                e.getEventEndDate(),
                e.isHasLocation(),
                e.getLocation(),
                e.getAttachments() == null ? null
                        : e.getAttachments().stream()
                                .map(AttachmentMapper::entityToDomain)
                                .collect(Collectors.toList()));
    }

    public static EventDTO domainToDTO(Event event) {
        return new EventDTO(event.getId(),
                event.getEventType(),
                event.getEventSize(),
                event.getAuthorId(),
                event.getCommunityId(),
                event.getTitle(),
                event.getDescription(),
                event.getCreationDate(),
                event.getExpirationDate(),
                event.getUpvoteCount(),
                event.getDownvoteCount(),
                event.isHasTime(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.isHasLocation(),
                event.getLocation(),
                false,
                false,
                false,
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
                e.expirationDate,
                e.upvoteCount,
                e.downvoteCount,
                e.hasTime,
                e.eventStartDate,
                e.eventEndDate,
                e.hasLocation,
                e.location,
                e.attachments);
    }
}
