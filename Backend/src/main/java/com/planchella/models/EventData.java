package com.planchella.models;

import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;

import java.util.Date;

public class EventData {
    public EventType eventType;
    public EventSize eventSize;
    public EventAuthorData authorData;
    public String title;
    public String description ;
    public String creationDate;

    public EventData(EventType eventType, EventSize eventSize, EventAuthorData authorData,
                     String title, String description,
                     String creationDate) {
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.title = title;
        this.authorData = authorData;
        this.description = description;
        this.creationDate = creationDate;
    }
    public static EventData getMockData(){
        return new EventData(EventType.HACKATHON, EventSize.LARGE, new EventAuthorData("", "Nour", ""), "Mock", "description", new Date().toString());
    }
}
