package com.planchella.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.entities.AttachmentEntity;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_author_id", columnList = "author_id"),
        @Index(name = "idx_community_id", columnList = "community_id")
})
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventEntity {
    @Id
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

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "custom_url")
    private String customUrl;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "expiration_time")
    private String expirationTime;

    @Column(name = "upvote_count")
    private Long upvoteCount;

    @Column(name = "downvote_count")
    private Long downvoteCount;

    // time related
    @Column(name = "has_time")
    private boolean hasTime;

    @Column(name = "event_start_date")
    private String eventStartDate;

    @Column(name = "event_end_date")
    private String eventEndDate;

    // location related
    @Column(name = "has_location")
    private boolean hasLocation;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<AttachmentEntity> attachments;

    public EventEntity(EventType eventType, EventSize eventSize, UserEntity author,
            String title, String description, String customUrl,
            String creationDate, String expirationTime, Long upvoteCount, Long downvoteCount,
            boolean hasTime, String eventStartDate, String eventEndDate,
            boolean hasLocation, Double latitude, Double longitude, String location,
            CommunityEntity community) {

        this.eventType = eventType;
        this.eventSize = eventSize;
        this.title = title;
        this.author = author;
        this.description = description;
        this.customUrl = customUrl;
        this.creationDate = creationDate;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;

        this.hasTime = hasTime;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;

        this.hasLocation = hasLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;

        this.community = community;
    }

    public EventEntity() {
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
                ", customUrl='" + customUrl + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", upvoteCount=" + upvoteCount +
                ", downvoteCount=" + downvoteCount +
                ", eventStartDate='" + eventStartDate + '\'' +
                ", eventEndDate='" + eventEndDate + '\'' +
                '}';
    }
}
