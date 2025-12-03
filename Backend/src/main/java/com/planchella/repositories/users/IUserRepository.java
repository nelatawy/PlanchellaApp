package com.planchella.repositories.users;

import com.planchella.domain.User;

import java.util.List;

public interface IUserRepository {

    List<User> getUsers(int count, Long community_id);

    User getUser(Long user_id);

    void deleteUser(Long user_id);

    void saveUser(User user);

}
