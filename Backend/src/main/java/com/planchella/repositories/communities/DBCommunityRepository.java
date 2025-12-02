package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import com.planchella.domain.CommunityData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBCommunityRepository implements ICommunityRepository {
    SessionFactory sessionFactory;
    public DBCommunityRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();

    }


    @Override
    public List<CommunityData> getCommunities(int count, String username) {
        Session session = sessionFactory.openSession();
//        Query query = session.createQuery("from Community where username = :username");
        return List.of();
    }

    public void saveCommunity(Community community) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(community);
        tx.commit();
        session.close();
    }

    public void closeFetcher(){
        this.sessionFactory.close();
    }

    public static void main(String[] args) {

    }

}
