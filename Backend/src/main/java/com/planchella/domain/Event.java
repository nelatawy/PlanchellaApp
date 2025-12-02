package com.planchella.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import jakarta.persistence.*;

import java.util.Date;

public class Event {

    private Long id;
    private EventType eventType;
    private EventSize eventSize;
    private Long author_id;
    private Long community_id;
    private String title;
    private String description ;
    private String creationDate;

    public Event(EventType eventType, EventSize eventSize, Long author_id,
                 String title, String description,
                 String creationDate, Long community_id) {
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.title = title;
        this.author_id = author_id;
        this.description = description;
        this.creationDate = creationDate;
        this.community_id = community_id;
    }

    public Event() {
        this.title = "";
    }

    public void updateByDelta(Event event){
        this.eventType    = event.eventType != null ? event.eventType : this.eventType;
        this.eventSize    = event.eventSize != null ? event.eventSize : this.eventSize;
        this.title        = event.title != null ? event.title : this.title;
        this.author_id    = event.author_id != null ? event.author_id : this.author_id;
        this.description  = event.description != null ? event.description : this.description;
        this.creationDate = event.creationDate != null ? event.creationDate : this.creationDate;
        this.community_id = event.community_id != null ? event.community_id : this.community_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventSize getEventSize() {
        return eventSize;
    }

    public void setEventSize(EventSize eventSize) {
        this.eventSize = eventSize;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public Long getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Long community_id) {
        this.community_id = community_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public static Event getMockData(){
        return new Event(EventType.HACKATHON, EventSize.LARGE,
                4L,
                "Mock", "description",
                new Date().toString(), 1L);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventType=" + eventType +
                ", eventSize=" + eventSize +
                ", author_id=" + author_id +
                ", community_id=" + community_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
