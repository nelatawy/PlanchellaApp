package com.planchella.Services;

import com.planchella.domain.Event;
import com.planchella.domain.User;
import com.planchella.entities.StarEntity;
import com.planchella.mappers.EventMapper;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.events.StarRepository;
import com.planchella.repositories.users.IUserRepository;
import com.planchella.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private StarRepository starRepo;

    @Autowired
    private IEventRepository eventRepo;

    public User getUser(Long userId) {
        return userRepo.getUser(userId);
    }

    public List<User> getUsers(Long communityId, int count, int offset) {
        return userRepo.getUsers(communityId, count, offset);
    }

    public List<Event> getUserEvents(Long userId, int count, int offset) {
        return eventRepo.getEventsByAuthor(userId, count, offset);
    }

    public List<Event> getStarredEvents(Long userId, int count, int offset) {
        if (count <= 0)
            return new ArrayList<>();
        Pageable pageable = PageRequest.of(offset / count, count);
        List<StarEntity> stars = starRepo.findByUserId(userId, pageable);

        return stars.stream()
                .map(star -> EventMapper.entityToDomain(star.getEvent()))
                .collect(Collectors.toList());
    }

    public void updateUser(Long userId, User newUserData) {
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

    public void deleteUser(Long userId) {
        userRepo.deleteUser(userId);
    }

}
