package com.planchella.Services;

import com.planchella.DTOs.EventDTO;
import com.planchella.domain.Event;
import com.planchella.entities.VoteEntity;
import com.planchella.enums.VoteType;
import com.planchella.mappers.EventMapper;
import com.planchella.repositories.events.StarRepository;
import com.planchella.repositories.events.VoteRepository;
import com.planchella.repositories.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSpecificEventService {

    @Autowired
    private StarRepository starRepo;

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private IUserRepository userRepo;

    public EventDTO enrichEventForUser(Event event, Long userId) {
        EventDTO eventDTO = EventMapper.domainToDTO(event);
        if (userId != null) {
            eventDTO.isStarred = isStarred(userId, event.getId());
            eventDTO.isUpvoted = isVoted(userId, event.getId(), VoteType.UPVOTE);
            eventDTO.isDownVoted = isVoted(userId, event.getId(), VoteType.DOWNVOTE);
        }
        return eventDTO;
    }

    public List<EventDTO> enrichEventsForUser(List<Event> events, Long userId) {
        return events.stream()
                .map(event -> enrichEventForUser(event, userId))
                .collect(Collectors.toList());
    }

    public boolean isVoted(Long userId, Long eventId, VoteType voteType) {
        if (userRepo.getUser(userId) == null) {
            return false;
        }
        Optional<VoteEntity> existingVote = voteRepo.findByUserIdAndEventId(userId, eventId);
        return existingVote.isPresent() && existingVote.get().getVoteType() == voteType;
    }

    public boolean isStarred(Long userId, Long eventId) {
        if (userRepo.getUser(userId) == null) {
            return false;
        }
        return starRepo.findByUserIdAndEventId(userId, eventId).isPresent();
    }
}
