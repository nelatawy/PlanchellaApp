package com.planchella.Services;

import com.planchella.domain.AttachmentMetadata;
import com.planchella.domain.Event;
import com.planchella.repositories.events.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private IEventRepository eventRepo;

    @Autowired
    private AttachmentService attachmentService;

    public Event getEvent(Long eventId) {
        return eventRepo.getEvent(eventId);
    }

    public void updateEvent(Long eventId, Event newEventData) {
        Event event = eventRepo.getEvent(eventId);
        for (AttachmentMetadata attachment : event.getAttachments()){
            if (!attachmentService.isAcknowledged(attachment.getId()))
                throw new IllegalArgumentException("No attachment uploaded with that attachment ID");
        }
        event.updateByDelta(newEventData);
        eventRepo.saveEvent(event);
    }

    public void addEvent(Event event) {
        for (AttachmentMetadata attachment : event.getAttachments()){
            if (!attachmentService.isAcknowledged(attachment.getId()))
                throw new IllegalArgumentException("No attachment uploaded with that attachment ID");
        }
        eventRepo.saveEvent(event);
    }

    public void deleteEvent(Long eventId) {
        eventRepo.deleteEvent(eventId);
    }
}
