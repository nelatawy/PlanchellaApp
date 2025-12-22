package com.planchella.Services;

import com.planchella.domain.*;
import com.planchella.entities.VoteEntity;
import com.planchella.enums.MembershipType;
import com.planchella.enums.VoteType;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.events.StarRepository;
import com.planchella.repositories.events.VoteRepository;
import com.planchella.repositories.users.IUserRepository;
import com.planchella.utils.IdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.planchella.entities.StarEntity;
import com.planchella.entities.UserEntity;
import com.planchella.entities.EventEntity;

@Service
public class EventService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IEventRepository eventRepo;

    @Autowired
    private StarRepository starRepo;

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private IUserRepository userRepo;

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
        User user = userRepo.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null || event == null || !Objects.equals(event.getAuthorId(), userId)) {
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
        User author = userRepo.getUser(event.getAuthorId());
        Community community = communityService.getCommunity(event.getCommunityId());

        // Verify the author can post in the community
        if (!membershipService.canUserPostIn(author, community)) {
            throw new IllegalStateException("Author does not have permission to post in this community");
        }

        Membership membership = membershipService.getMembership(author.getId(), community.getId());
        for (AttachmentMetadata attachment : event.getAttachments()) {
            if (!attachmentService.isAcknowledged(attachment.getId()))
                throw new IllegalArgumentException("No attachment uploaded with that attachment ID");
        }
        event.setId(IdGenerator.generateId());
        event.setDownvoteCount(0L);
        event.setUpvoteCount(0L);
        Instant now = Instant.now();
        event.setCreationDate(now.toString());
        Instant expirationTime;
        if (membership.getType() == MembershipType.CREATOR) {
            expirationTime = now.plus(6, ChronoUnit.DAYS);
        } else {
            expirationTime = now.plus(3, ChronoUnit.DAYS);
        }
        event.setExpirationDate(expirationTime.toString());
        if (event.getEventStartDate() != null || event.getEventEndDate() != null) {
            event.setHasTime(true);
        } else {
            event.setHasTime(false);
        }

        if (event.getLocation() != null) {
            event.setHasLocation(true);
        } else {
            event.setHasLocation(false);
        }
        System.out.println(event);
        eventRepo.saveEvent(event);
    }

    public void deleteEvent(Long eventId, Long userId) {
        User user = userRepo.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null || event == null || !Objects.equals(event.getAuthorId(), userId)) {
            throw new IllegalArgumentException(
                    "Event doesn't exist or the user doesn't have the authority to delete it");
        }
        eventRepo.deleteEvent(eventId);
    }

    @Transactional
    public void toggleStarEvent(Long eventId, Long userId) {
        User user = userRepo.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null || event == null) {
            throw new IllegalArgumentException("Event or User doesn't exist");
        }

        Optional<StarEntity> existingStar = starRepo.findByUserIdAndEventId(userId, eventId);
        if (existingStar.isPresent()) {
            starRepo.deleteByUserIdAndEventId(userId, eventId);
        } else {
            StarEntity star = new StarEntity();
            star.setUser(entityManager.getReference(UserEntity.class, userId));
            star.setEvent(entityManager.getReference(EventEntity.class, eventId));
            starRepo.save(star);
        }
    }

    @Transactional
    public void voteEvent(Long userId, Long eventId, VoteType voteType) {
        User user = userRepo.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null || event == null) {
            throw new IllegalArgumentException("Event or User doesn't exist");
        }

        Optional<VoteEntity> existingVote = voteRepo.findByUserIdAndEventId(userId, eventId);
        if (existingVote.isPresent()) {
            VoteEntity vote = existingVote.get();
            if (vote.getVoteType() == voteType) {
                return;
            }

            if (vote.getVoteType() == VoteType.UPVOTE) {
                event.setUpvoteCount(event.getUpvoteCount() - 1);
            } else {
                event.setDownvoteCount(event.getDownvoteCount() - 1);
            }
            vote.setVoteType(voteType);
            voteRepo.save(vote);
        } else {
            VoteEntity vote = new VoteEntity();
            vote.setUser(entityManager.getReference(UserEntity.class, userId));
            vote.setEvent(entityManager.getReference(EventEntity.class, eventId));
            vote.setVoteType(voteType);
            voteRepo.save(vote);
        }

        if (voteType == VoteType.UPVOTE) {
            event.setUpvoteCount(event.getUpvoteCount() + 1);
        } else {
            event.setDownvoteCount(event.getDownvoteCount() + 1);
        }
        eventRepo.saveEvent(event);
    }

    @Transactional
    public void removeVoteEvent(Long userId, Long eventId) {
        User user = userRepo.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null || event == null) {
            throw new IllegalArgumentException("Event or User doesn't exist");
        }

        Optional<VoteEntity> existingVote = voteRepo.findByUserIdAndEventId(userId, eventId);
        if (existingVote.isPresent()) {
            VoteEntity vote = existingVote.get();
            if (vote.getVoteType() == VoteType.UPVOTE) {
                event.setUpvoteCount(event.getUpvoteCount() - 1);
            } else {
                event.setDownvoteCount(event.getDownvoteCount() - 1);
            }
            voteRepo.deleteByUserIdAndEventId(userId, eventId);
            eventRepo.saveEvent(event);
        }
    }

}
