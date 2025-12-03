package com.planchella.repositories.users;

import com.planchella.domain.User;
import java.util.ArrayList;
import java.util.List;

public class MockUserRepository implements IUserRepository {
    @Override
    public List<User> getUsers(int count, Long community_id) {
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
    public User getUser(Long user_id) {
        return new User(
                user_id,
                "hamada",
                "",
                ""
                );
    }

    @Override
    public void deleteUser(Long user_id) {

    }

    @Override
    public void saveUser(User user) {

    }

}
