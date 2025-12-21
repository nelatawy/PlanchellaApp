package com.planchella.Services;

import com.planchella.domain.Community;
import com.planchella.domain.Membership;
import com.planchella.domain.User;
import com.planchella.enums.MembershipType;
import com.planchella.repositories.memberships.IMembershipRepository;
import com.planchella.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    @Autowired
    private IMembershipRepository membershipRepository;

    // Service-layer caching - survives across object instances
    private final ConcurrentHashMap<Long, List<Membership>> userMembershipsCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, List<Membership>> communityMembershipsCache = new ConcurrentHashMap<>();

    public Membership getMembership(Long membershipId) {
        return membershipRepository.getMembership(membershipId);
    }

    public Membership getMembership(Long userId, Long communityId) {
        return membershipRepository.getMembership(userId, communityId);
    }

    /**
     * Get all memberships for a user with caching.
     */
    public List<Membership> getMembershipsByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userMembershipsCache.computeIfAbsent(user.getId(),
                userId -> membershipRepository.getMembershipsByUser(userId));
    }

    /**
     * Get all memberships for a community with caching.
     */
    public List<Membership> getMembershipsByCommunity(Community community) {
        if (community == null) {
            throw new IllegalArgumentException("Community cannot be null");
        }
        return communityMembershipsCache.computeIfAbsent(community.getId(),
                communityId -> membershipRepository.getMembershipsByCommunity(communityId));
    }

    /**
     * Get the number of members in a community.
     */
    public Long getMemberCount(Long communityId) {
        if (communityId == null) {
            return 0L;
        }
        // This will use the cache if available
        return (long) membershipRepository.getMembershipsByCommunity(communityId).size();
    }

    /**
     * Business logic: Check if user can post in a community.
     */
    public boolean canUserPostIn(User user, Community community) {
        if (user == null || community == null) {
            return false;
        }
        System.out.println(getMembershipsByUser(user).size());
        return getMembershipsByUser(user).stream()
                .anyMatch(m -> m.getCommunityId().equals(community.getId()) && m.canPost());
    }

    /**
     * Business logic: Get all member IDs with a specific role in a community.
     */
    public List<Long> getMemberIDsByRole(Community community, MembershipType type) {
        return getMembershipsByCommunity(community).stream()
                .filter(m -> m.getType() == type)
                .map(Membership::getUserId)
                .collect(Collectors.toList());
    }

    /**
     * Add a membership with cache invalidation.
     * Transactional to ensure membership is saved and cache invalidated atomically.
     */
    @Transactional
    public void addMembership(User user, Community community, MembershipType type) {
        System.out.println(user + "\n" + "\n" + type);
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (community == null) {
            throw new IllegalArgumentException("Community cannot be null");
        }

        Membership membership = new Membership(IdGenerator.generateId(), user.getId(), community.getId(), type);
        saveMembership(membership);

        // Invalidate caches (write-through)
        invalidateUserCache(user.getId());
        invalidateCommunityCache(community.getId());
    }

    public void removeMembership(Membership membership) {
        membershipRepository.deleteMembership(membership);
    }

    private void saveMembership(Membership membership) {
        membershipRepository.saveMembership(membership);
    }

    // Cache management
    public void invalidateUserCache(Long userId) {
        userMembershipsCache.remove(userId);
    }

    public void invalidateCommunityCache(Long communityId) {
        communityMembershipsCache.remove(communityId);
    }

    public void clearAllCaches() {
        userMembershipsCache.clear();
        communityMembershipsCache.clear();
    }
}
