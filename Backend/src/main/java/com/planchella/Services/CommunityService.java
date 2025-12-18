package com.planchella.Services;

import com.planchella.domain.Community;
import com.planchella.repositories.communities.ICommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {

    @Autowired
    private ICommunityRepository communityRepo;

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
}
