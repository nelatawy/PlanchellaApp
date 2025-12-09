package com.planchella;

import com.planchella.DTOs.EventDTO;
import com.planchella.domain.Event;
import com.planchella.mappers.EventMapper;
import com.planchella.repositories.events.DBEventRepository;
import com.planchella.repositories.events.IEventRepository;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventRoutes {
    IEventRepository eventRepository;
    public EventRoutes() {
        this.eventRepository = new DBEventRepository();
    }

    @GetMapping("/{event_id}")
    public EventDTO getEvent(@PathVariable Long event_id){
        Event event = this.eventRepository.getEvent(event_id);
        return EventMapper.domainToDTO(event);
    }

    @PatchMapping("/{event_id}")
    public void updateEvent(@PathVariable Long event_id, @RequestBody EventDTO data) {
        Event newEventData = EventMapper.DTOtoDomain(data);
        Event event = this.eventRepository.getEvent(event_id);
        event.updateByDelta(newEventData);
        this.eventRepository.saveEvent(event);
    }

    @PutMapping
    public void addEvent(@RequestBody EventDTO data) {
        Event event = EventMapper.DTOtoDomain(data);
        this.eventRepository.saveEvent(event);
    }

    @DeleteMapping("/{event_id}")
    public void deleteEvent(@PathVariable Long event_id) {
        this.eventRepository.deleteEvent(event_id);
    }

}
