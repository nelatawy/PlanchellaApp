package com.planchella.repositories.communities;

import com.planchella.Configs.HibernateUtil;
import com.planchella.domain.Community;
import com.planchella.entities.CommunityEntity;
import com.planchella.mappers.CommunityMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DBCommunityRepository implements ICommunityRepository {
    SessionFactory sessionFactory;

    public DBCommunityRepository() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Community> getCommunitiesByAuthor(int count, Long userId) {
        Session session = sessionFactory.openSession();
        Query<CommunityEntity> query = session.createQuery(
                "select c from CommunityEntity c " +
                        "join c.memberships m " +
                        "join m.user u where u.id = :ID",
                CommunityEntity.class);
        query.setParameter("ID", userId);
        List<CommunityEntity> results = query.getResultList();
        List<Community> communities = new ArrayList<>();
        for (CommunityEntity communityEntity : results) {
            Community community = CommunityMapper.entityToDomain(communityEntity);
            communities.add(community);
        }
        session.close();
        return communities;
    }

    @Override
    public Community getCommunity(Long communityId) {
        Session session = sessionFactory.openSession();
        CommunityEntity communityEntity = (CommunityEntity) session.get(CommunityEntity.class, communityId);
        Community community = CommunityMapper.entityToDomain(communityEntity);
        session.close();
        return community;
    }
    //
    // @Override
    // public void updateCommunity(Long community_id, Community community) {
    // Session session = sessionFactory.openSession();
    // CommunityEntity communityEntity = (CommunityEntity)
    // session.get(CommunityEntity.class, community_id);
    //
    // Transaction tx = session.beginTransaction();
    // if (community != null) {
    // if(community.getName() != null && !community.getName().isEmpty())
    // communityEntity.setName(community.getName());
    // }
    // tx.commit();
    // session.close();
    // }

    @Override
    public void saveCommunity(Community community) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        CommunityEntity communityEntity = CommunityMapper.domainToEntity(community, session);
        if (community.getId() != null) {
            session.persist(communityEntity);
        } else {
            session.merge(communityEntity);
        }
        tx.commit();
        session.close();
    }

    @Override
    public void deleteCommunity(Long communityId) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(session.get(CommunityEntity.class, communityId));
        tx.commit();
        session.close();
    }

    public void closeFetcher() {
        this.sessionFactory.close();
    }

    public static void main(String[] args) {

    }

}
