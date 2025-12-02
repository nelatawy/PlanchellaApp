package com.planchella.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import jakarta.persistence.*;

import java.util.Date;

public class Event {

    public Long id;
    public EventType eventType;
    public EventSize eventSize;
    public User author;
    public Community community;
    public String title;
    public String description ;
    public String creationDate;

    public Event(EventType eventType, EventSize eventSize, User author,
                 String title, String description,
                 String creationDate, Community community) {
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.title = title;
        this.author = author;
        this.description = description;
        this.creationDate = creationDate;
        this.community = community;
    }

    public Event() {
        this.title = "";
    }

    public static Event getMockData(){
        return new Event(EventType.HACKATHON, EventSize.LARGE,
                new User("", "Nour", ""),
                "Mock", "description",
                new Date().toString(), new Community(""));
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventType=" + eventType +
                ", eventSize=" + eventSize +
                ", author=" + author +
                ", community=" + community +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }



}
