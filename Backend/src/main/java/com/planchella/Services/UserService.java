package com.planchella.Services;

import com.planchella.domain.User;
import com.planchella.repositories.users.IUserRepository;
import com.planchella.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepo;

    public User getUser(Long userId) {
        return userRepo.getUser(userId);
    }

    public List<User> getUsers(Long communityId, int count, int offset) {
        return userRepo.getUsers(communityId, count, offset);
    }

    public void updateUser(Long userId, User newUserData, Long requestingUserId) {
        if (!userId.equals(requestingUserId)) {
            throw new IllegalArgumentException("Users can only update their own profile");
        }
        User user = userRepo.getUser(userId);
        if (user != null) {
            user.updateByDelta(newUserData);
            userRepo.saveUser(user);
        }
    }

    public void addUser(User user) {
        user.setId(IdGenerator.generateId());
        userRepo.saveUser(user);
    }

    public void deleteUser(Long userId, Long requestingUserId) {
        if (!userId.equals(requestingUserId)) {
            throw new IllegalArgumentException("Users can only delete their own profile");
        }
        userRepo.deleteUser(userId);
    }

}
