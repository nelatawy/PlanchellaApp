package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import com.planchella.entities.CommunityEntity;
import com.planchella.mappers.CommunityMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DBCommunityRepository implements ICommunityRepository {
    SessionFactory sessionFactory;
    CommunityMapper mapper;

    public DBCommunityRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.mapper = new CommunityMapper(this.sessionFactory);

    }

    @Override
    public List<Community> getCommunities(int count, Long user_id) {
        Session session = sessionFactory.openSession();
        Query<CommunityEntity> query = session.createQuery(
                                          "select c from CommunityEntity c " +
                                             "join c.memberships m " +
                                             "join m.user u where u.id = :user_id", CommunityEntity.class);
        List<CommunityEntity> results = query.getResultList();
        List<Community> communities = new ArrayList<Community>();
        for (CommunityEntity communityEntity : results) {
            Community community = mapper.entityToDomain(communityEntity);
            communities.add(community);
        }
        session.close();
        return communities;
    }



    @Override
    public Community getCommunity(Long community_id){
        Session session = sessionFactory.openSession();
        CommunityEntity communityEntity = (CommunityEntity) session.get(CommunityEntity.class, community_id);
        Community community = mapper.entityToDomain(communityEntity);
        session.close();
        return community;
    }

    @Override
    public void updateCommunity(Long community_id, Community community) {
        Session session =  sessionFactory.openSession();
        CommunityEntity communityEntity = (CommunityEntity) session.get(CommunityEntity.class, community_id);

        Transaction tx = session.beginTransaction();
        if (community != null) {
            if(community.getName() != null && !community.getName().isEmpty())
                communityEntity.setName(community.getName());
        }
        tx.commit();
        session.close();
    }

    @Override
    public void saveCommunity(Community community) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(mapper.domainToEntity(community));
        tx.commit();
        session.close();
    }

    @Override
    public void deleteCommunity(Long community_id){
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(session.get(CommunityEntity.class, community_id));
        tx.commit();
        session.close();
    }



    public void closeFetcher(){
        this.sessionFactory.close();
    }

    public static void main(String[] args) {

    }

}
