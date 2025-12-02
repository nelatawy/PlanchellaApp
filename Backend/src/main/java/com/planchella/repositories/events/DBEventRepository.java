package com.planchella.repositories.events;

import com.planchella.domain.Event;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBEventRepository implements IEventRepository {
    SessionFactory sessionFactory;
    public DBEventRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();

    }
    @Override
    public List<Event> getEvents(int count, Long community_id) {
        Session session = this.sessionFactory.openSession();

        String hql = "FROM Event e WHERE e.community.id = :ID";
        Query query = session.createQuery(hql, Event.class);
        query.setParameter("ID", community_id);

        List<Event> events = query.getResultList();
        session.close();
        return events;
    }
    @Override
    public List<Event> getEvents(int count, String communityName) {
        Session session = this.sessionFactory.openSession();

        String hql = "FROM Event e WHERE e.community.name = :NAME";
        Query query = session.createQuery(hql, Event.class);
        query.setParameter("NAME", communityName);
        query.setMaxResults(count);

        List<Event> events = query.getResultList();
        session.close();
        return events;
    }

    public void saveEvent(Event event) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(event);
        tx.commit();
        session.close();
    }

    public void closeFetcher(){
        this.sessionFactory.close();
    }

    public static void main(String[] args) {
        DBEventRepository dbEventFetcher = new DBEventRepository();
        List<Event> events = dbEventFetcher.getEvents(4, 1L);
        for(Event event : events){
            System.out.println(event);
        }
    }
}
