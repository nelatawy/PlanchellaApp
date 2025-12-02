package com.planchella;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planchella.DTOs.EventDTO;
import com.planchella.repositories.communities.ICommunityRepository;
import com.planchella.repositories.communities.MockCommunityRepository;
import com.planchella.repositories.events.DBEventRepository;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.domain.Event;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "http://localhost:4200")
public class EventRoutes {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final IEventRepository eventFetcher = new DBEventRepository();
    private static final ICommunityRepository communityFetcher = new MockCommunityRepository();

    @GetMapping("/events")
    public String getEvent(@RequestParam int count , @RequestParam String community_name) throws JsonProcessingException {
//        try {
//            JSONArray events_json = new JSONArray();
//            List<Event> events = eventFetcher.getEvents(count, communityName);
//
//            for (Event event : events) {
//                JSONObject event_json = new JSONObject(mapper.writeValueAsString(event));
//                events_json.put(event_json);
//            }
//            return events_json.toString();
//        }catch (Exception e){
//            System.err.println(e.getMessage());
//        }
        return "";
    }

    @GetMapping("/events")
    public String getEvent(@RequestParam int count , @RequestParam Long community_id) throws JsonProcessingException {
        try {
            JSONArray jsonevents = new JSONArray();
            List<Event> events = eventFetcher.getEvents(count, community_id);
            for (Event event : events) {
                //TODO: some conversion logic to DTO if needed
                jsonevents.put(event);
            }
            return jsonevents.toString();

        }catch (Exception e){
            System.err.println("Error getting events: " + e.getMessage());
        }
        return "";
    }

    @PostMapping("/events")
    public String addEvent(@RequestParam int count , @RequestParam EventDTO dto) throws JsonProcessingException {
        try {
            Event event = new Event();
            event.eventType = dto.eventType;
            event.eventSize = dto.eventSize;
            event.title = dto.title;
            event.description = dto.description;
            event.author = null;
            event.community = ;

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return "";
    }


}