package com.planchella.mappers;

import com.planchella.DTOs.EventDTO;
import com.planchella.domain.Event;
import com.planchella.entities.CommunityEntity;
import com.planchella.entities.EventEntity;
import com.planchella.entities.UserEntity;
import org.hibernate.Session;

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
        return e;
    }

    public static Event entityToDomain(EventEntity e){

        return new Event(e.getId(),
                e.getEventType(),
                e.getEventSize(),
                e.getAuthor().getId(),
                e.getCommunity().getId(),
                e.getTitle(),
                e.getDescription(),
                e.getCreationDate());
    }

    public static EventDTO domainToDTO(Event event){
        EventDTO e = new EventDTO();
        e.id = event.getId();
        e.description = event.getDescription();
        e.eventType = event.getEventType();
        e.creationDate = event.getCreationDate();
        e.eventSize = event.getEventSize();
        e.title = event.getTitle();
        e.community_id = event.getCommunity_id();
        return e;
    }

    public static Event DTOtoDomain(EventDTO e){

        return new Event(e.id,
                e.eventType,
                e.eventSize,
                e.author_id,
                e.community_id,
                e.title,
                e.description,
                e.creationDate);
    }

}
