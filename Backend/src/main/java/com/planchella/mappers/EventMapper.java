package com.planchella.mappers;

import com.planchella.domain.Event;
import com.planchella.entities.CommunityEntity;
import com.planchella.entities.EventEntity;
import com.planchella.entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class EventMapper {
    SessionFactory factory;
    public EventMapper(SessionFactory factory) {
        this.factory = factory;
    }

    public EventEntity domainToEntity(Event event){
        Session session = factory.openSession();

        EventEntity e = new EventEntity();

        e.setId(event.getId());
        e.setDescription(event.getDescription());
        e.setAuthor(session.getReference(UserEntity.class, event.getAuthor_id()));
        e.setCommunity(session.getReference(CommunityEntity.class, event.getCommunity_id()));
        e.setTitle(event.getTitle());
        e.setCreationDate(event.getCreationDate());
        e.setEventSize(event.getEventSize());
        e.setEventType(event.getEventType());

        session.close();
        return e;
    }

    public Event EntityToDomain(EventEntity e){
        Session session = factory.openSession();

        Event event = new Event();

        event.setId(e.getId());
        event.setDescription(e.getDescription());
        event.setEventType(e.getEventType());
        event.setCreationDate(e.getCreationDate());
        event.setEventSize(e.getEventSize());
        event.setTitle(e.getTitle());
        event.setAuthor_id(e.getAuthor().getId());
        event.setCommunity_id(e.getCommunity().getId());

        session.close();
        return event;
    }

}
