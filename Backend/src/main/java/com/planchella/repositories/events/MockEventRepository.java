package com.planchella.repositories.events;

import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import com.planchella.domain.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MockEventRepository implements IEventRepository {
    @Override
    public List<Event> getEventsByCommunity(Long communityId, int count, int offset) {
        List<Event> events = new ArrayList<>();
        Event event = Event.getMockData();

        for (int i = 0; i < count; i++) {
            if (Objects.equals(communityId, 0L)) {
                event.setEventType(EventType.CONTEST);
            }
            events.add(event);
        }
        return events;
    }

    @Override
    public List<Event> getEventsByAuthor(Long userId, int count, int offset) {
        return List.of();
    }

    @Override
    public Event getEvent(Long eventId) {
        return new Event(
                eventId,
                EventType.CONTEST,
                EventSize.LARGE,
                10L,
                3L,
                "title",
                "description",
                new Date().toString(),
                0L,
                0L,
                new Date().toString(),
                new Date().toString(),
                List.of());
    }

    @Override
    public void deleteEvent(Long eventId) {

    }

    @Override
    public void saveEvent(Event event) {
        return;
    }

    @Override
    public List<Event> searchEvents(String keywords, int count, int offset) {
        return List.of();
    }

    @Override
    public List<Event> searchInCommunities(String keywords, Long communityId, int count, int offset) {
        return List.of();
    }

}
