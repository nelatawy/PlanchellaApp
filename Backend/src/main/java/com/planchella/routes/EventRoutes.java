package com.planchella.routes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import com.planchella.DTOs.EventDTO;
import com.planchella.Services.EventService;
import com.planchella.domain.Event;
import com.planchella.mappers.EventMapper;
import com.planchella.utils.UserAuthenticationHelper;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:4200/")
public class EventRoutes {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserAuthenticationHelper authHelper;

    @GetMapping("/{event_id}")
    public ResponseEntity<?> getEvent(@PathVariable Long event_id) {
        try {
            Event event = this.eventService.getEvent(event_id);
            return ResponseEntity.ok().body(EventMapper.domainToDTO(event));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage()));
        }
    }

    @PatchMapping("/{event_id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long event_id, @RequestBody EventDTO data,
            @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = authHelper.extractUserId(authHeader);
            Event newEventData = EventMapper.DTOtoDomain(data);
            this.eventService.updateEvent(event_id, userId, newEventData);
            return ResponseEntity.ok().body(Map.of(
                    "message", "updated event successfully"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> addEvent(@RequestBody EventDTO data, @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = authHelper.extractUserId(authHeader);
            Event event = EventMapper.DTOtoDomain(data);
            // Set the author ID from the authenticated user
            event.setAuthorId(userId);
            this.eventService.addEvent(event);
            return ResponseEntity.ok().body(Map.of(
                    "message", "added event successfully"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "can't add event"));
        }
    }

    @DeleteMapping("/{event_id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long event_id,
            @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = authHelper.extractUserId(authHeader);
            this.eventService.deleteEvent(event_id, userId);
            return ResponseEntity.ok().body(Map.of(
                    "message", "deleted event successfully"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "can't delete event"));
        }
    }

}
