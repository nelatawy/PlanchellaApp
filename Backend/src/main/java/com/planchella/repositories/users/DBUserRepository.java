package com.planchella.repositories.users;

import java.util.ArrayList;
import java.util.List;

import com.planchella.Configs.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.query.Query;

import com.planchella.domain.User;
import com.planchella.entities.UserEntity;
import com.planchella.mappers.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DBUserRepository implements IUserRepository {
    SessionFactory sessionFactory;

    public DBUserRepository() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<User> getUsers(Long communityId, int count, int offset) {
        Session session = this.sessionFactory.openSession();

        String hql = "select e from UserEntity e " +
                "join e.memberships m " +
                "join m.community c " +
                "where c.id = :ID ";

        Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
        query.setParameter("ID", communityId);
        if (count > 0) {
            query.setMaxResults(count);
        }
        if (offset > 0) {
            query.setFirstResult(offset);
        }
        return getUsersHelper(session, query);
    }

    @Override
    public User getUser(Long userId) {
        Session session = this.sessionFactory.openSession();
        System.out.println(userId);
        UserEntity userEntity = session.get(UserEntity.class, userId);
        session.close();

        // Return null if user doesn't exist instead of trying to map null
        if (userEntity == null) {
            return null;
        }

        return UserMapper.entityToDomain(userEntity);
    }

    // @Override
    // public void updateUser(Long user_id, User newUserData){
    // Session session = this.sessionFactory.openSession();
    // Transaction tx = session.beginTransaction();
    // UserEntity user = session.get(UserEntity.class, user_id);
    // if(newUserData != null) {
    // if (user.getName() != null) user.setName(newUserData.getName());
    // if (user.getPicUrl() != null) user.setPicUrl(newUserData.getPicUrl());
    // if (user.getAccountUrl() != null)
    // user.setAccountUrl(newUserData.getAccountUrl());
    // }
    // tx.commit();
    // session.close();
    // }

    @Override
    public Long saveUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        UserEntity entity = UserMapper.domainToEntity(user, session);
        if (session.get(UserEntity.class, entity.getId()) == null) {
            session.persist(entity);
            session.flush();

        } else {
            session.merge(entity);
        }
        tx.commit();
        session.close();
        return entity.getId();
    }

    @Override
    public void deleteUser(Long userId) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(session.get(UserEntity.class, userId));
        tx.commit();
        session.close();
    }

    private List<User> getUsersHelper(Session session, Query<UserEntity> query) {
        List<UserEntity> users = query.getResultList();
        List<User> usersList = new ArrayList<>();
        for (UserEntity e : users) {
            User user = UserMapper.entityToDomain(e);
            usersList.add(user);
        }
        session.close();
        System.out.println(usersList.size());
        return usersList;
    }

    @Override
    public List<User> searchUsers(String name, int count, int offset) {
        Session session = this.sessionFactory.openSession();
        String hql = "select e from UserEntity e where lower(e.name) like lower(:name)";
        Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
        query.setParameter("name", "%" + name + "%");
        if (count > 0) {
            query.setMaxResults(count);
        }
        if (offset > 0) {
            query.setFirstResult(offset);
        }
        return getUsersHelper(session, query);
    }

    public void closeFetcher() {
        this.sessionFactory.close();
    }

}
