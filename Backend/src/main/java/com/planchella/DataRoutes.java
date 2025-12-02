package com.planchella;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planchella.repositories.communities.ICommunityRepository;
import com.planchella.repositories.communities.MockCommunityRepository;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.events.MockEventRepository;
import com.planchella.domain.CommunityData;
import com.planchella.domain.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "http://localhost:4200")
public class DataRoutes {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final IEventRepository eventFetcher = new MockEventRepository();
    private static final ICommunityRepository communityFetcher = new MockCommunityRepository();

    @GetMapping("/community/events")
    public String fetchEvent(@RequestParam int count , @RequestParam String communityName) throws JsonProcessingException {
        try {
            JSONArray events_json = new JSONArray();
            List<Event> events = eventFetcher.getEvents(count, communityName);

            for (Event event : events) {
                JSONObject event_json = new JSONObject(mapper.writeValueAsString(event));
                events_json.put(event_json);
            }
            return events_json.toString();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return "";
    }

    @GetMapping("/user/communities")
    public String fetchCommunities(@RequestParam int count , @RequestParam String username) throws JsonProcessingException {
        try {
            JSONArray communities_json = new JSONArray();
            List<CommunityData> communities = communityFetcher.getCommunities(count, username);

            for (CommunityData communityData : communities) {
                JSONObject community_json = new JSONObject(mapper.writeValueAsString(communityData));
                communities_json.put(community_json);
            }
            return communities_json.toString();

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return "";
    }


}
