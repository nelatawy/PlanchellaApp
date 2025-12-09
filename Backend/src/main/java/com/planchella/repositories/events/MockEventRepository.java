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
    public List<Event> getEvents(int count, Long community_id) {
        List<Event> events = new ArrayList<>();
        Event event = Event.getMockData();

        for (int i = 0; i < count; i++) {
            if (Objects.equals(community_id, 0L)){
                event.setEventType(EventType.CONTEST);
            }
            events.add(event);
        }
        return events;
    }

    public List<Event> getEvents(int count, String communityName) {
        List<Event> events = new ArrayList<>();
        Event event = Event.getMockData();

        for (int i = 0; i < count; i++) {
            if (Objects.equals(communityName, "CSED")){
                event.setEventType(EventType.CONTEST);
            }
            events.add(event);
        }
        return events;
    }

    @Override
    public Event getEvent(Long event_id) {
        return new Event(
                event_id,
                EventType.CONTEST,
                EventSize.LARGE,
                10L,
                3L,
                "title",
                "description",
                new Date().toString());
    }


    @Override
    public void deleteEvent(Long event_id) {

    }

    @Override
    public void saveEvent(Event event) {
        return;
    }

}
