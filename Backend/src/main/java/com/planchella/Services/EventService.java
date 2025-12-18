package com.planchella.Services;

import com.planchella.domain.Event;
import com.planchella.repositories.events.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private IEventRepository eventRepo;

    public Event getEvent(Long eventId) {
        return eventRepo.getEvent(eventId);
    }

    public void updateEvent(Long eventId, Event newEventData) {
        Event event = eventRepo.getEvent(eventId);
        if (event != null) {
            event.updateByDelta(newEventData);
            eventRepo.saveEvent(event);
        }
    }

    public void addEvent(Event event) {
        eventRepo.saveEvent(event);
    }

    public void deleteEvent(Long eventId) {
        eventRepo.deleteEvent(eventId);
    }
}
