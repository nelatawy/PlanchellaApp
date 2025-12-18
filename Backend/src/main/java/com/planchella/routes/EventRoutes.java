package com.planchella.routes;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planchella.DTOs.EventDTO;
import com.planchella.Services.EventService;
import com.planchella.domain.Event;
import com.planchella.mappers.EventMapper;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:4200/")
public class EventRoutes {
    @Autowired
    private EventService eventService;

    @GetMapping("/{event_id}")
    public EventDTO getEvent(@PathVariable Long event_id) {
        Event event = this.eventService.getEvent(event_id);
        return EventMapper.domainToDTO(event);
    }

    @PatchMapping("/{event_id}")
    public void updateEvent(@PathVariable Long event_id, @RequestBody EventDTO data) {
        Event newEventData = EventMapper.DTOtoDomain(data);
        this.eventService.updateEvent(event_id, newEventData);
    }

    @PutMapping
    public void addEvent(@RequestBody EventDTO data) {
        Event event = EventMapper.DTOtoDomain(data);
        this.eventService.addEvent(event);
    }

    @DeleteMapping("/{event_id}")
    public void deleteEvent(@PathVariable Long event_id) {
        this.eventService.deleteEvent(event_id);
    }

}
