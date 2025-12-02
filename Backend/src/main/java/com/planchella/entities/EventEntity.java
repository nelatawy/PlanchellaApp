package com.planchella.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_size")
    private EventSize eventSize;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "community_id")
    private CommunityEntity community;

    private String title;
    private String description ;
    private String creationDate;

    public EventEntity(EventType eventType, EventSize eventSize, UserEntity author,
                 String title, String description,
                 String creationDate, CommunityEntity community) {
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.title = title;
        this.author = author;
        this.description = description;
        this.creationDate = creationDate;
        this.community = community;
    }
    public EventEntity() {}



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

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public CommunityEntity getCommunity() {
        return community;
    }

    public void setCommunity(CommunityEntity community) {
        this.community = community;
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
