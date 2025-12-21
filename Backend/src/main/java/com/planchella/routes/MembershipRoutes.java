package com.planchella.routes;

import com.planchella.Services.CommunityService;
import com.planchella.Services.MembershipService;
import com.planchella.Services.UserService;
import com.planchella.domain.Community;
import com.planchella.domain.Membership;
import com.planchella.domain.User;
import com.planchella.enums.MembershipType;
import com.planchella.utils.UserAuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin(origins = "http://localhost:4200/")
public class MembershipRoutes {

    @Autowired
    MembershipService membershipService;

    @Autowired
    UserAuthenticationHelper authHelper;

    @Autowired
    UserService userService;

    @Autowired
    CommunityService communityService;

    @PostMapping("/join/{community_id}/guest")
    public void joinCommunityAsGuest(@PathVariable Long community_id, @RequestHeader("Authorization") String authHeader){
        Long userId = authHelper.extractUserId(authHeader);
        User user = userService.getUser(userId);
        Community community = communityService.getCommunity(community_id);
        this.membershipService.addMembership(user, community, MembershipType.GUEST);
    }

    @PostMapping("/join/{community_id}/member")
    public void joinCommunityAsMember(@PathVariable Long community_id, @RequestHeader("Authorization") String authHeader){
        Long userId = authHelper.extractUserId(authHeader);
        User user = userService.getUser(userId);
        Community community = communityService.getCommunity(community_id);
        this.membershipService.addMembership(user, community, MembershipType.MEMBER);
    }

    @PostMapping("/leave/{community_id}")
    public void leaveCommunity(@PathVariable Long community_id, @RequestHeader("Authorization") String authHeader) {
        Long userId = authHelper.extractUserId(authHeader);
        Membership membership = membershipService.getMembership(userId, community_id);
        if (membership == null){
            return;
        }
        this.membershipService.removeMembership(membership);
    }
}
