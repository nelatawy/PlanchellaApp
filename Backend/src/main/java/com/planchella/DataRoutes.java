package com.planchella;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planchella.fetcher.communities.ICommunityFetcher;
import com.planchella.fetcher.communities.MockCommunityFetcher;
import com.planchella.fetcher.events.IEventFetcher;
import com.planchella.fetcher.events.MockEventFetcher;
import com.planchella.models.CommunityData;
import com.planchella.models.EventData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "http://localhost:4200")
public class DataRoutes {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final IEventFetcher eventFetcher = new MockEventFetcher();
    private static final ICommunityFetcher communityFetcher = new MockCommunityFetcher();

    @GetMapping("/community/events")
    public String fetchEvent(@RequestParam int count , @RequestParam String communityName) throws JsonProcessingException {
        try {
            JSONArray events_json = new JSONArray();
            List<EventData> events = eventFetcher.getEvents(count, communityName);

            for (EventData event : events) {
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
