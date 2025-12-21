package com.planchella.Services;

import com.planchella.domain.AttachmentMetadata;
import com.planchella.domain.Community;
import com.planchella.domain.Event;
import com.planchella.domain.User;
import com.planchella.entities.VoteEntity;
import com.planchella.enums.VoteType;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.events.StarRepository;
import com.planchella.repositories.events.VoteRepository;
import com.planchella.repositories.users.IUserRepository;
import com.planchella.utils.IdGenerator;
import org.hibernate.boot.model.internal.IdBagBinder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        if (user == null || event == null || !Objects.equals(event.getAuthorId(), userId)) {
            throw new IllegalArgumentException(
                    "Event doesn't exist or the user doesn't have the authority to delete it");
        }
        eventRepo.deleteEvent(eventId);
    }

    @Transactional
    public void toggleStarEvent(Long eventId, Long userId) {
        User user = userService.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null || event == null) {
            throw new IllegalArgumentException("Event or User doesn't exist");
        }

        Optional<StarEntity> existingStar = starRepo.findByUserIdAndEventId(userId, eventId);
        if (existingStar.isPresent()) {
            starRepo.deleteByUserIdAndEventId(userId, eventId);
            event.setStarred(false);
        } else {
            StarEntity star = new StarEntity();
            star.setUser(entityManager.getReference(UserEntity.class, userId));
            star.setEvent(entityManager.getReference(EventEntity.class, eventId));
            starRepo.save(star);
            event.setStarred(true);
        }
    }

    @Transactional
    public void voteEvent(Long userId, Long eventId, VoteType voteType) {
        User user = userService.getUser(userId);
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
                event.setUpvoted(false);
            } else {
                event.setDownvoteCount(event.getDownvoteCount() - 1);
                event.setDownVoted(false);
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
            event.setUpvoted(true);
        } else {
            event.setDownvoteCount(event.getDownvoteCount() + 1);
            event.setDownVoted(true);
        }
        eventRepo.saveEvent(event);
    }

    @Transactional
    public void removeVoteEvent(Long userId, Long eventId) {
        User user = userService.getUser(userId);
        Event event = eventRepo.getEvent(eventId);
        if (user == null || event == null) {
            throw new IllegalArgumentException("Event or User doesn't exist");
        }

        Optional<VoteEntity> existingVote = voteRepo.findByUserIdAndEventId(userId, eventId);
        if (existingVote.isPresent()) {
            VoteEntity vote = existingVote.get();
            if (vote.getVoteType() == VoteType.UPVOTE) {
                event.setUpvoteCount(event.getUpvoteCount() - 1);
                event.setUpvoted(false);
            } else {
                event.setDownvoteCount(event.getDownvoteCount() - 1);
                event.setDownVoted(false);
            }
            voteRepo.deleteByUserIdAndEventId(userId, eventId);
            eventRepo.saveEvent(event);
        }
    }
}
