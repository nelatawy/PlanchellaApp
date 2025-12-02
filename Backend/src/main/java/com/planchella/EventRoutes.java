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
    EventMapper mapper;
    public EventRoutes() {
        this.eventRepository = new DBEventRepository();
        this.mapper = new EventMapper(new Configuration().configure().buildSessionFactory());
    }

    @GetMapping("/{event_id}")
    public EventDTO getEvent(@PathVariable Long event_id){
        Event event = this.eventRepository.getEvent(event_id);
        return this.mapper.domainToDTO(event);
    }

    @PatchMapping("/{event_id}")
    public void updateEvent(@PathVariable Long event_id, @RequestBody EventDTO data) {
        Event event = this.mapper.DTOtoDomain(data);
        event.setId(null);
        this.eventRepository.updateEvent(event_id, event);
    }

    @PutMapping
    public void addEvent(@RequestBody EventDTO data) {
        Event event = this.mapper.DTOtoDomain(data);
        event.setId(null);
        this.eventRepository.saveEvent(event);
    }

    @DeleteMapping("/{event_id}")
    public void deleteEvent(@PathVariable Long event_id) {
        this.eventRepository.deleteEvent(event_id);
    }
}
