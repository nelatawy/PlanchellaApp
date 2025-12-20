package com.planchella.repositories.users;

import com.planchella.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository {

    List<User> getUsers(Long communityId, int count, int offset);

    User getUser(Long userId);

    void deleteUser(Long userId);

    Long saveUser(User user);

}
