package com.planchella;

import com.planchella.DTOs.EventDTO;
import com.planchella.mappers.EventMapper;
import com.planchella.repositories.events.DBEventRepository;
import com.planchella.repositories.events.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.domain.Community;
import com.planchella.mappers.CommunityMapper;
import com.planchella.repositories.communities.DBCommunityRepository;
import com.planchella.repositories.communities.ICommunityRepository;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityRoutes {
    @Autowired
    DBCommunityRepository communityRepo;

    @Autowired
    DBEventRepository eventRepository;



    @GetMapping("/{community_id}")
    public CommunityDTO getCommunity(@PathVariable Long community_id) {
        Community community = this.communityRepo.getCommunity(community_id);
        return CommunityMapper.domainToDTO(community);
    }

    @PatchMapping("/{community_id}")
    public void updateCommunity(@PathVariable Long community_id, @RequestBody CommunityDTO data) {
        Community newCommunityData = CommunityMapper.DTOtoDomain(data);
        Community community = this.communityRepo.getCommunity(community_id);
        community.updateByDelta(newCommunityData);
        this.communityRepo.saveCommunity(community);
    }

    @PutMapping
    public void addCommunity(@RequestBody CommunityDTO data) {
        Community community = CommunityMapper.DTOtoDomain(data);
        this.communityRepo.saveCommunity(community);
    }

    @DeleteMapping("/{community_id}")
    public void deleteCommunity(@PathVariable Long community_id) {
        this.communityRepo.deleteCommunity(community_id);
    }

//    @GetMapping("/{community_id}/events")
//    public List<EventDTO> getEvents(@PathVariable Long community_id, @RequestParam int count){
//
//    }
}
