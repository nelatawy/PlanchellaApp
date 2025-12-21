package com.planchella.Services;

import com.planchella.domain.AttachmentMetadata;
import com.planchella.domain.Community;
import com.planchella.domain.Event;
import com.planchella.domain.User;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.utils.IdGenerator;
import org.hibernate.boot.model.internal.IdBagBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EventService {

    @Autowired
    private IEventRepository eventRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private MembershipService membershipService;
    @Autowired
    private CommunityService communityService;


    public Event getEvent(Long eventId) {
        return eventRepo.getEvent(eventId);
    }

    public void updateEvent(Long eventId, Long userId, Event newEventData) {
        User user = userService.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null | event == null | !Objects.equals(event.getAuthorId(), userId)) {
            throw new IllegalArgumentException("Event doesn't exist or the user doesn't have the authority to edit it");
        }

        for (AttachmentMetadata attachment : event.getAttachments()) {
            if (!attachmentService.isAcknowledged(attachment.getId()))
                throw new IllegalArgumentException("No attachment uploaded with that attachment ID");
        }
        event.updateByDelta(newEventData);
        eventRepo.saveEvent(event);
    }

    public void addEvent(Event event) {
        User author = userService.getUser(event.getAuthorId());
        Community community = communityService.getCommunity(event.getCommunityId());

        // Verify the author can post in the community
        if (!membershipService.canUserPostIn(author, community)) {
            throw new IllegalStateException("Author does not have permission to post in this community");
        }

        for (AttachmentMetadata attachment : event.getAttachments()) {
            if (!attachmentService.isAcknowledged(attachment.getId()))
                throw new IllegalArgumentException("No attachment uploaded with that attachment ID");
        }
        event.setId(IdGenerator.generateId());
        event.setDownvoteCount(0L);
        event.setUpvoteCount(0L);
        eventRepo.saveEvent(event);
    }

    public void deleteEvent(Long eventId, Long userId) {
        User user = userService.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null | event == null | !Objects.equals(event.getAuthorId(), userId)) {
            throw new IllegalArgumentException(
                    "Event doesn't exist or the user doesn't have the authority to delete it");
        }
        eventRepo.deleteEvent(eventId);
    }
}
