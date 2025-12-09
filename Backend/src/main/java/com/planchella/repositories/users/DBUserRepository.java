package com.planchella.repositories.users;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.planchella.domain.User;
import com.planchella.entities.UserEntity;
import com.planchella.mappers.UserMapper;

public class DBUserRepository implements IUserRepository {
    SessionFactory sessionFactory;

    public DBUserRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public List<User> getUsers(int count, Long community_id) {
        Session session = this.sessionFactory.openSession();

        String hql = "select e from UserEntity e " +
                "join e.memberships m " +
                "join m.community c " +
                "where c.id = :ID ";

        Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
        query.setParameter("ID", community_id);

        return getUsersHelper(session, query);
    }

    @Override
    public User getUser(Long user_id) {
        Session session = this.sessionFactory.openSession();
        UserEntity userEntity = session.get(UserEntity.class, user_id);
        User user = UserMapper.entityToDomain(userEntity);
        session.close();
        return user;
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
    public void saveUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        UserEntity entity = UserMapper.domainToEntity(user, session);
        if (user.getId() == null) {
            session.persist(entity);
        } else {
            session.merge(entity);
        }
        tx.commit();
        session.close();
    }

    @Override
    public void deleteUser(Long user_id) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(session.get(UserEntity.class, user_id));
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
        return usersList;
    }

    public void closeFetcher() {
        this.sessionFactory.close();
    }

}
