package com.planchella.routes;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.DTOs.EventDTO;
import com.planchella.DTOs.UserDTO;
import com.planchella.Services.SearchService;
import com.planchella.mappers.CommunityMapper;
import com.planchella.mappers.EventMapper;
import com.planchella.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchRoutes {

    @Autowired
    private SearchService searchService;

    @GetMapping("/events")
    public List<EventDTO> searchEvents(@RequestParam String keywords, @RequestParam int count, @RequestParam int offset) {
        System.out.println(keywords);
        return searchService.searchEvents(keywords, count, offset)
                .stream().map(EventMapper::domainToDTO).toList();
    }

    @GetMapping("/communities")
    public List<CommunityDTO> searchCommunities(@RequestParam String keywords, @RequestParam int count, @RequestParam int offset){
        System.out.println(keywords);
        return searchService.searchCommunity(keywords, count, offset)
                .stream().map(CommunityMapper::domainToDTO).toList();
    }

    @GetMapping("/communities/{community_id}/events")
    public List<EventDTO> searchInCommunities(@RequestParam String keywords, @PathVariable Long community_id,
                                                  @RequestParam int count, @RequestParam int offset){
        System.out.println(keywords);
        return searchService.searchEventsInCommunity(keywords, community_id, count, offset)
                .stream().map(EventMapper::domainToDTO).toList();
    }


    @GetMapping("/users")
    public List<UserDTO> searchUsers(@RequestParam String keywords, @RequestParam int count, @RequestParam int offset){
        System.out.println(keywords);
        return searchService.searchUser(keywords, count, offset)
                .stream().map(UserMapper::domainToDTO).toList();
    }
}
