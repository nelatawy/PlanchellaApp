package com.planchella.repositories.users;

import java.util.ArrayList;
import java.util.List;

import com.planchella.domain.User;

public class MockUserRepository implements IUserRepository {
    @Override
    public List<User> getUsers(Long communityId, int count, int offset) {
        List<User> users = new ArrayList<>();
        User user = User.getMockData();

        for (int i = 0; i < count; i++) {
            users.add(user);
        }
        return users;
    }

    public List<User> getUsers(int count, String communityName) {
        List<User> users = new ArrayList<>();
        User user = User.getMockData();

        for (int i = 0; i < count; i++) {
            users.add(user);
        }
        return users;
    }

    @Override
    public User getUser(Long userId) {
        return new User(
                userId,
                "hamada@example.com",
                "hamada",
                "",
                "");
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public Long saveUser(User user) {
        return 1L;
    }

    @Override
    public List<User> searchUsers(String name, int count, int offset) {
        return List.of();
    }

}
