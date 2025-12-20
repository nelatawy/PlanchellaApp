package com.planchella.routes;

import com.planchella.DTOs.EventDTO;
import com.planchella.Services.EventService;
import com.planchella.domain.Event;
import com.planchella.mappers.EventMapper;
import com.planchella.utils.UserAuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.Services.CommunityService;
import com.planchella.domain.Community;
import com.planchella.mappers.CommunityMapper;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityRoutes {
    @Autowired
    CommunityService communityService;

    @Autowired
    EventService eventService;

    @Autowired
    UserAuthenticationHelper authHelper;

    @GetMapping("/{community_id}")
    public CommunityDTO getCommunity(@PathVariable Long community_id) {
        Community community = this.communityService.getCommunity(community_id);
        return CommunityMapper.domainToDTO(community);
    }

    @GetMapping("/{community_id}/events")
    public List<EventDTO> getCommunityEvents(@PathVariable Long community_id, @RequestParam int count,
            @RequestParam int offset) {
        List<Event> events = eventService.getEventsByCommunity(community_id, count, offset);
        return events.stream().map(EventMapper::domainToDTO).toList();
    }

    @PatchMapping("/{community_id}")
    public void updateCommunity(@PathVariable Long community_id, @RequestBody CommunityDTO data,
            @RequestHeader("Authorization") String authHeader) {
        Long userId = authHelper.extractUserId(authHeader);
        System.out.println(userId);
        Community newCommunityData = CommunityMapper.DTOtoDomain(data);
        System.out.println(newCommunityData);
        this.communityService.updateCommunity(community_id, userId, newCommunityData);
    }

    @PutMapping
    public void addCommunity(@RequestBody CommunityDTO data, @RequestHeader("Authorization") String authHeader) {
        Long userId = authHelper.extractUserId(authHeader);
        System.out.println(userId);
        Community community = CommunityMapper.DTOtoDomain(data);
        System.out.println(community);
        this.communityService.addCommunity(community, userId);
    }

    @DeleteMapping("/{community_id}")
    public void deleteCommunity(@PathVariable Long community_id, @RequestHeader("Authorization") String authHeader) {
        Long userId = authHelper.extractUserId(authHeader);
        System.out.println(userId);
        this.communityService.deleteCommunity(community_id, userId);
    }

    // @GetMapping("/{community_id}/events")
    // public List<EventDTO> getEvents(@PathVariable Long community_id,
    // @RequestParam int count){
    //
    // }
}
