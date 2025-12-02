package com.planchella.repositories.events;

import com.planchella.enums.EventType;
import com.planchella.domain.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MockEventRepository implements IEventRepository {
    @Override
    public List<Event> getEvents(int count, Long community_id) {
        return new ArrayList<>();
    }

    @Override
    public List<Event> getEvents(int count, String communityName) {
        List<Event> events = new ArrayList<>();
        Event event = Event.getMockData();

        for (int i = 0; i < count; i++) {
            if (Objects.equals(communityName, "CSED")){
                event.eventType = EventType.CONTEST;
            }
            events.add(event);
        }
        return events;
    }
}
