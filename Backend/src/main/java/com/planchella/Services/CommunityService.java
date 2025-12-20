package com.planchella.Services;

import com.planchella.domain.Community;
import com.planchella.domain.Event;
import com.planchella.repositories.communities.ICommunityRepository;
import com.planchella.repositories.events.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private ICommunityRepository communityRepo;

    @Autowired
    private IEventRepository eventRepo;

    public Community getCommunity(Long communityId) {
        return communityRepo.getCommunity(communityId);
    }

    public void updateCommunity(Long communityId, Community newCommunityData) {
        Community community = communityRepo.getCommunity(communityId);
        if (community != null) {
            community.updateByDelta(newCommunityData);
            communityRepo.saveCommunity(community);
        }
    }

    public void addCommunity(Community community) {
        communityRepo.saveCommunity(community);
    }

    public void deleteCommunity(Long communityId) {
        communityRepo.deleteCommunity(communityId);
    }

    public List<Event> getCommunityEvents(Long communityId, int count, int offset){
        return eventRepo.getEventsByCommunity(communityId, count, offset);
    }
}
