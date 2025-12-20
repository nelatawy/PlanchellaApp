package com.planchella.Services;

import com.planchella.domain.Event;
import com.planchella.domain.User;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IEventRepository eventRepo;

    public User getUser(Long userId) {
        return userRepo.getUser(userId);
    }

    public void updateUser(Long userId, User newUserData) {
        User user = userRepo.getUser(userId);
        if (user != null) {
            user.updateByDelta(newUserData);
            userRepo.saveUser(user);
        }
    }

    public void addUser(User user) {
        userRepo.saveUser(user);
    }

    public void deleteUser(Long userId) {
        userRepo.deleteUser(userId);
    }

    public List<Event> getUserEvents(Long userId, int count, int offset){
        return eventRepo.getEventsByAuthor(userId, count, offset);
    }

}
