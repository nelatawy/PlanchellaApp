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
    EventMapper mapper;
    public DBEventRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.mapper = new EventMapper(this.sessionFactory);

    }
    @Override
    public List<Event> getEvents(int count, Long community_id) {
        Session session = this.sessionFactory.openSession();

        String hql = "FROM EventEntity e WHERE e.community.id = :ID";
        Query<EventEntity> query = session.createQuery(hql, EventEntity.class);
        query.setParameter("ID", community_id);

        return getEventsHelper(session, query);
    }

    @Override
    public List<Event> getEvents(int count, String communityName) {
        Session session = this.sessionFactory.openSession();

        String hql = "FROM EventEntity e WHERE e.community.name = :NAME";
        Query<EventEntity> query = session.createQuery(hql, EventEntity.class);
        query.setParameter("NAME", communityName);

        return getEventsHelper(session, query);
    }

    @Override
    public Event getEvent(Long event_id){
        Session session = this.sessionFactory.openSession();
        EventEntity eventEntity = session.get(EventEntity.class, event_id);
        session.close();
        return this.mapper.EntityToDomain(eventEntity);
    }


    @Override
    public void updateEvent(Long event_id, Event event){
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        EventEntity eventEntity = session.get(EventEntity.class, event_id);
        tx.commit();
        session.close();
    }

    @Override
    public void saveEvent(Event event) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(this.mapper.domainToEntity(event));
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
            Event event = this.mapper.EntityToDomain(e);
            eventsList.add(event);
        }
        session.close();
        return eventsList;
    }



    public void closeFetcher(){
        this.sessionFactory.close();
    }


    //    @Override
//    public List<Event> getEvents(int count, String communityName) {
//        Session session = this.sessionFactory.openSession();
//
//        String hql = "FROM Event e WHERE e.community.name = :NAME";
//        Query query = session.createQuery(hql, Event.class);
//        query.setParameter("NAME", communityName);
//        query.setMaxResults(count);
//
//        List<Event> events = query.getResultList();
//        session.close();
//        return events;
//    }

    public static void main(String[] args) {
        DBEventRepository dbEventFetcher = new DBEventRepository();
        List<Event> events = dbEventFetcher.getEvents(4, 1L);
        for(Event event : events){
            System.out.println(event);
        }
    }
}
