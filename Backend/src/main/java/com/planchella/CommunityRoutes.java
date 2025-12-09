package com.planchella;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.domain.Community;
import com.planchella.mappers.CommunityMapper;
import com.planchella.repositories.communities.DBCommunityRepository;
import com.planchella.repositories.communities.ICommunityRepository;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/communities")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityRoutes {
    ICommunityRepository communityRepo;

    public CommunityRoutes() {
        this.communityRepo = new DBCommunityRepository();
    }

    @GetMapping("/{community_id}")
    public CommunityDTO getCommunity(@PathVariable Long community_id){
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
}
