package com.planchella.repositories.events;

import com.planchella.domain.Event;
import com.planchella.entities.EventEntity;
import com.planchella.mappers.EventMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DBEventRepository implements IEventRepository {
    SessionFactory sessionFactory;
    public DBEventRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();

    }
    @Override
    public List<Event> getEvents(int count, Long community_id) {
        Session session = this.sessionFactory.openSession();

        String hql = "from EventEntity e where e.community.id = :ID";
        Query<EventEntity> query = session.createQuery(hql, EventEntity.class);
        query.setParameter("ID", community_id);

        return getEventsHelper(session, query);
    }


    @Override
    public Event getEvent(Long event_id){
        Session session = this.sessionFactory.openSession();
        EventEntity eventEntity = session.get(EventEntity.class, event_id);
        Event event = EventMapper.entityToDomain(eventEntity);
        session.close();
        return event;
    }

//    @Override
//    public void updateEvent(Long event_id, Event newEventData){
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        EventEntity event = session.get(EventEntity.class, event_id);
//        if (newEventData != null) {
//            if (event.getDescription() != null) event.setDescription(newEventData.getDescription());
//            if (event.getTitle() != null) event.setTitle(newEventData.getTitle());
//            if (event.getEventSize() != null) event.setEventSize(newEventData.getEventSize());
//            if (event.getEventType() != null) event.setEventType(newEventData.getEventType());
//            if (event.getCreationDate() != null) event.setCreationDate(newEventData.getCreationDate());
//            if (event.getAuthor() != null) event.setAuthor(session.getReference(UserEntity.class, newEventData.getAuthor_id()));
//            if (event.getCommunity() != null) event.setCommunity(session.getReference(CommunityEntity.class, newEventData.getCommunity_id()));
//        }
//        tx.commit();
//        session.close();
//    }

    @Override
    public void saveEvent(Event event) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        EventEntity entity = EventMapper.domainToEntity(event, session);
        if (event.getId() == null) {
            session.persist(entity);
        } else {
            session.merge(entity);
        }
        tx.commit();
        session.close();
    }


    @Override
    public void deleteEvent(Long event_id){
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(session.get(EventEntity.class, event_id));
        tx.commit();
        session.close();
    }


    private List<Event> getEventsHelper(Session session, Query<EventEntity> query) {
        List<EventEntity> events = query.getResultList();
        List<Event> eventsList = new ArrayList<Event>();
        for (EventEntity e : events) {
            Event event = EventMapper.entityToDomain(e);
            eventsList.add(event);
        }
        session.close();
        return eventsList;
    }



    public void closeFetcher(){
        this.sessionFactory.close();
    }

}
