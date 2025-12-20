package com.planchella.repositories.memberships;

import com.planchella.Configs.HibernateUtil;
import com.planchella.domain.Membership;
import com.planchella.entities.MembershipEntity;
import com.planchella.enums.MembershipType;
import com.planchella.mappers.MembershipMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DBMembershipRepository implements IMembershipRepository {
    SessionFactory sessionFactory;

    public DBMembershipRepository() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Membership getMembership(Long membershipId) {
        Session session = sessionFactory.openSession();
        MembershipEntity entity = session.get(MembershipEntity.class, membershipId);
        Membership membership = MembershipMapper.entityToDomain(entity);
        session.close();
        return membership;
    }

    public Membership getMembership(Long userId, Long communityId) {
        Session session = sessionFactory.openSession();
        String hql = "from MembershipEntity e where e.user.id = :UserId and e.community.id = :CommunityId";
        Query<MembershipEntity> query = session.createQuery(hql, MembershipEntity.class);
        query.setParameter("UserId", userId);
        query.setParameter("CommunityId", communityId);
        query.setMaxResults(1);
        return MembershipMapper.entityToDomain(query.getResultList().getFirst());
    }

    public List<Membership> getMembershipsByUser(Long userId) {
        Session session = sessionFactory.openSession();
        String hql = "from MembershipEntity e where e.user.id = :userId";
        Query<MembershipEntity> query = session.createQuery(hql, MembershipEntity.class);
        query.setParameter("userId", userId);
        List<Membership> memberships = query.getResultList().stream()
                .map(MembershipMapper::entityToDomain)
                .collect(Collectors.toList());
        session.close();
        return memberships;
    }

    public List<Membership> getMembershipsByCommunity(Long communityId) {
        Session session = sessionFactory.openSession();
        String hql = "from MembershipEntity e where e.community.id = :communityId";
        Query<MembershipEntity> query = session.createQuery(hql, MembershipEntity.class);
        query.setParameter("communityId", communityId);
        List<Membership> memberships = query.getResultList().stream()
                .map(MembershipMapper::entityToDomain)
                .collect(Collectors.toList());
        session.close();
        return memberships;
    }

    public List<Membership> getUserMembershipsByRole(Long userId, MembershipType type) {
        Session session = sessionFactory.openSession();
        String hql = "from MembershipEntity e where e.community.id = :UserId and e.type = :Type";
        Query<MembershipEntity> query = session.createQuery(hql, MembershipEntity.class);
        query.setParameter("UserId", userId);
        query.setParameter("Type", type);
        List<Membership> memberships = query.getResultList().stream()
                .map(MembershipMapper::entityToDomain)
                .collect(Collectors.toList());
        session.close();
        return memberships;
    }

    public void saveMembership(Membership membership) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        MembershipEntity entity = MembershipMapper.domainToEntity(membership, session);
        if (session.get(MembershipEntity.class, entity.getId()) == null) {
            session.persist(entity);
        } else {
            session.merge(entity);
        }
        tx.commit();
        session.close();
    }

    public void deleteMembership(Membership membership) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        MembershipEntity entity = MembershipMapper.domainToEntity(membership, session);
        if (session.get(MembershipEntity.class, entity.getId()) == null) {
            session.remove(entity);
        }
        tx.commit();
        session.close();
    }

}
