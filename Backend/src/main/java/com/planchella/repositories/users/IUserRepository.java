package com.planchella.repositories.users;

import com.planchella.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository {

    List<User> getUsers(int count, Long communityId);

    User getUser(Long userId);

    void deleteUser(Long userId);

    Long saveUser(User user);

}
