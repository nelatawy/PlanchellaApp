package com.planchella.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
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
