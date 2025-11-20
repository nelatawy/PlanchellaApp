package com.planchella.fetcher.events;

import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import com.planchella.models.EventAuthorData;
import com.planchella.models.EventData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MockEventFetcher implements IEventFetcher{
    @Override
    public List<EventData> getEvents(int count, String communityName) {
        List<EventData> events = new ArrayList<>();
        EventData eventData = new EventData(EventType.HACKATHON, EventSize.LARGE,
                                            new EventAuthorData("", "Nour", ""),
                                            "Mock", "description", new Date().toString());

        for (int i = 0; i < count; i++) {
            if (Objects.equals(communityName, "CSED")){
                eventData.eventType = EventType.CONTEST;
            }
            events.add(eventData);
        }
        return events;
    }
}
