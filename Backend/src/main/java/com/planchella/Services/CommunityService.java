package com.planchella.Services;

import com.planchella.domain.Community;
import com.planchella.domain.Event;
import com.planchella.domain.Membership;
import com.planchella.domain.User;
import com.planchella.enums.MembershipType;
import com.planchella.repositories.communities.ICommunityRepository;
import com.planchella.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private ICommunityRepository communityRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private MembershipService membershipService;

    public Community getCommunity(Long communityId) {
        return communityRepo.getCommunity(communityId);
    }

    public void updateCommunity(Long communityId, Long userId, Community newCommunityData) {
        Membership membership = membershipService.getMembership(userId, communityId);
        if (membership == null || membership.getType() != MembershipType.CREATOR) {
            throw new IllegalArgumentException("Users can't edit communities that they haven't created");
        }
        Community community = communityRepo.getCommunity(communityId);
        if (community != null) {
            community.updateByDelta(newCommunityData);
            communityRepo.saveCommunity(community);
        }
    }

    @Transactional
    public void addCommunity(Community community, Long userId) {
        // Validate user exists before creating community
        User user = userService.getUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist. Cannot create community.");
        }

        // Generate and set ID for the community
        community.setId(IdGenerator.generateId());

        communityRepo.saveCommunity(community);

        // Create creator membership for the user who created the community
        // Use existing community object - don't re-fetch within same transaction!
        membershipService.addMembership(user, community, MembershipType.CREATOR);
    }

    @Transactional
    public void deleteCommunity(Long communityId, Long userId) {
        Membership membership = membershipService.getMembership(userId, communityId);
        if (membership == null || membership.getType() != MembershipType.CREATOR) {
            throw new IllegalArgumentException("Users can only delete communities that they have created");
        }
        communityRepo.deleteCommunity(communityId);
    }

}
