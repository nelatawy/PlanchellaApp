package com.planchella.repositories.events;

import com.planchella.domain.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventRepository {

    List<Event> getEventsByCommunity(Long communityId, int count, int offset);

    List<Event> getEventsByAuthor(Long userId, int count, int offset);

    Event getEvent(Long eventId);

    void deleteEvent(Long eventId);

    void saveEvent(Event event);

    List<Event> searchEvents(String keywords, int count, int offset);

}
