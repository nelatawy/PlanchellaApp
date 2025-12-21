package com.planchella.Services;

import com.planchella.domain.Community;
import com.planchella.domain.Event;
import com.planchella.domain.User;
import com.planchella.repositories.communities.ICommunityRepository;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    IEventRepository eventRepo;

    @Autowired
    ICommunityRepository communityRepo;

    @Autowired
    IUserRepository userRepo;

    public List<Event> searchEvents(String searchTerm, int count, int offset) {
        return eventRepo.searchEvents(searchTerm, count, offset);
    }

    public List<Community> searchCommunity(String searchTerm, int count, int offset) {
        return communityRepo.SearchCommunities(searchTerm, count, offset);
    }

    public List<User> searchUser(String searchTerm, int count, int offset) {
        return userRepo.searchUsers(searchTerm, count, offset);
    }
}
