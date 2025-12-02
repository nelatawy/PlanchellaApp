package com.planchella.repositories.events;

import com.planchella.domain.Event;

import java.util.List;

public interface IEventRepository {
    List<Event> getEvents(int count, Long community_id);
    List<Event> getEvents(int count, String communityName);
}
