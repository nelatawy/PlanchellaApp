//package com.planchella.repositories;
//
//import com.planchella.DTOs.CommunityDTO;
//import com.planchella.DTOs.EventDTO;
//import com.planchella.domain.Community;
//import com.planchella.repositories.communities.DBCommunityRepository;
//import com.planchella.repositories.communities.ICommunityRepository;
//import com.planchella.repositories.events.DBEventRepository;
//import com.planchella.repositories.events.IEventRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/community")
//@CrossOrigin(origins = "http://localhost:4200")
//public class EventRoutes {
//    IEventRepository eventRepository;
//    public EventRoutes() {
//        this.eventRepository = new DBEventRepository();
//    }
//
//    @GetMapping("/{event_id}")
//    public EventDTO getEvent(@PathVariable Long event_id){
//        Community community = this.eventRepository.getCommunity(community_id);
//        //TODO:DTO mapping logic
//        CommunityDTO communityDTO = new CommunityDTO();
//        communityDTO.id = community.getId();
//        communityDTO.name = community.getName();
//        return communityDTO;
//    }
//
//    @PatchMapping("/{community_id}")
//    public void updateCommunity(@PathVariable Long community_id, @RequestBody CommunityDTO data) {
//        // TODO : mapper from DTO to domain
//        Community community = new Community();
//        community.setName(data.name);
//        this.communityRepo.updateCommunity(data.id, community);
//    }
//
//    @PutMapping
//    public void addCommunity(@RequestBody CommunityDTO data) {
//        Community community = new Community();
//        community.setName("Community 1");
//        this.communityRepo.saveCommunity(community);
//    }
//
//    @DeleteMapping("/{community_id}")
//    public void deleteCommunity(@PathVariable Long community_id) {
//        this.communityRepo.deleteCommunity(community_id);
//    }
//}
