package com.planchella.fetcher.events;

import com.planchella.models.EventData;

import java.util.List;

public interface IEventFetcher {
    List<EventData> getEvents(int count, String communityName);
}
