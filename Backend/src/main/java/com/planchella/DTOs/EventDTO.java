package com.planchella.DTOs;

import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;

public class EventDTO {
    public Long id;
    public EventType eventType;
    public EventSize eventSize;
    public Long author_id;
    public Long community_id;
    public String title;
    public String description ;
    public String creationDate;
}
