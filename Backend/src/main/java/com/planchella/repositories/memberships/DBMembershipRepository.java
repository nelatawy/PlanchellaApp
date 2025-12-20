package com.planchella.repositories.memberships;

import com.planchella.Configs.HibernateUtil;
import com.planchella.domain.Membership;

import com.planchella.entities.MembershipEntity;

import com.planchella.mappers.MembershipMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

    public void saveMembership(Membership membership) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        MembershipEntity entity = MembershipMapper.domainToEntity(membership, session);
        if (membership.getId() == null) {
            session.persist(entity);
        } else {
            session.merge(entity);
        }
        tx.commit();
        session.close();
    }
}
