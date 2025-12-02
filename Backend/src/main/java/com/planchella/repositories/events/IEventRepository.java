package com.planchella.repositories.events;

import com.planchella.domain.Event;

import java.util.List;

public interface IEventRepository {
    List<Event> getEvents(int count, Long community_id);

    List<Event> getEvents(int count, String communityName);

    Event getEvent(Long event_id);

    void updateEvent(Long event_id, Event event);

    void deleteEvent(Long event_id);

    void saveEvent(Event event);
}
