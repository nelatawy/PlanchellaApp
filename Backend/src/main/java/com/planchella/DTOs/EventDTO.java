package com.planchella.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {
    public Long id;
    public EventType eventType;
    public EventSize eventSize;
    public Long author_id;
    public Long community_id;
    public String title;
    public String description ;
    public String creationDate;
    // list of attachment
    public  EventDTO() {}
}
