package com.planchella.repositories.events;

import com.planchella.domain.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventRepository {

    List<Event> getEventsByCommunity(int count, Long community_id);

    List<Event> getEventsByAuthor(int count, Long user_id);


    Event getEvent(Long event_id);

    void deleteEvent(Long event_id);

    void saveEvent(Event event);

}
