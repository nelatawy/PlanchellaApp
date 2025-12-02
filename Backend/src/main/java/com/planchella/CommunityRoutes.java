package com.planchella;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.domain.Community;
import com.planchella.repositories.communities.DBCommunityRepository;
import com.planchella.repositories.communities.ICommunityRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityRoutes {
    ICommunityRepository communityRepo;
    public CommunityRoutes() {
        this.communityRepo = new DBCommunityRepository();
    }

    @GetMapping("/{community_id}")
    public CommunityDTO getCommunity(@PathVariable Long community_id){
        Community community = this.communityRepo.getCommunity(community_id);
        //TODO:DTO mapping logic
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.id = community.getId();
        communityDTO.name = community.getName();
        return communityDTO;
    }

    @PatchMapping("/{community_id}")
    public void updateCommunity(@PathVariable Long community_id, @RequestBody CommunityDTO data) {
        // TODO : mapper from DTO to domain
        Community community = new Community();
        community.setName(data.name);
        this.communityRepo.updateCommunity(data.id, community);
    }

    @PutMapping
    public void addCommunity(@RequestBody CommunityDTO data) {
        Community community = new Community();
        community.setName("Community 1");
        this.communityRepo.saveCommunity(community);
    }

    @DeleteMapping("/{community_id}")
    public void deleteCommunity(@PathVariable Long community_id) {
        this.communityRepo.deleteCommunity(community_id);
    }
}
