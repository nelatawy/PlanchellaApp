package com.planchella;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.domain.Community;
import com.planchella.mappers.CommunityMapper;
import com.planchella.repositories.communities.DBCommunityRepository;
import com.planchella.repositories.communities.ICommunityRepository;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityRoutes {
    ICommunityRepository communityRepo;
    CommunityMapper mapper;
    public CommunityRoutes() {
        this.communityRepo = new DBCommunityRepository();
        this.mapper = new CommunityMapper(new Configuration().configure().buildSessionFactory());
    }

    @GetMapping("/{community_id}")
    public CommunityDTO getCommunity(@PathVariable Long community_id){
        Community community = this.communityRepo.getCommunity(community_id);
        return this.mapper.domainToDTO(community);
    }

    @PatchMapping("/{community_id}")
    public void updateCommunity(@PathVariable Long community_id, @RequestBody CommunityDTO data) {
        Community community = this.mapper.DTOtoEntity(data);
        community.setId(null);
        this.communityRepo.updateCommunity(community_id, community);
    }

    @PutMapping
    public void addCommunity(@RequestBody CommunityDTO data) {
        Community community = this.mapper.DTOtoEntity(data);
        community.setId(null);
        this.communityRepo.saveCommunity(community);
    }

    @DeleteMapping("/{community_id}")
    public void deleteCommunity(@PathVariable Long community_id) {
        this.communityRepo.deleteCommunity(community_id);
    }
}
