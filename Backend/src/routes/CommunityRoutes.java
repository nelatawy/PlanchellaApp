package com.planchella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.Services.CommunityService;
import com.planchella.Services.EventService;
import com.planchella.domain.Community;
import com.planchella.mappers.CommunityMapper;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityRoutes {
    @Autowired
    CommunityService communityService;

    @Autowired
    EventService eventService;

    @GetMapping("/{community_id}")
    public CommunityDTO getCommunity(@PathVariable Long community_id) {
        Community community = this.communityService.getCommunity(community_id);
        return CommunityMapper.domainToDTO(community);
    }

    @PatchMapping("/{community_id}")
    public void updateCommunity(@PathVariable Long community_id, @RequestBody CommunityDTO data) {
        Community newCommunityData = CommunityMapper.DTOtoDomain(data);
        this.communityService.updateCommunity(community_id, newCommunityData);
    }

    @PutMapping
    public void addCommunity(@RequestBody CommunityDTO data) {
        Community community = CommunityMapper.DTOtoDomain(data);
        this.communityService.addCommunity(community);
    }

    @DeleteMapping("/{community_id}")
    public void deleteCommunity(@PathVariable Long community_id) {
        this.communityService.deleteCommunity(community_id);
    }

    // @GetMapping("/{community_id}/events")
    // public List<EventDTO> getEvents(@PathVariable Long community_id,
    // @RequestParam int count){
    //
    // }
}
