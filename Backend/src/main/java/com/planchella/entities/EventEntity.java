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

    @Column(name = "creation_date")
    private String creationDate;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<AttachmentEntity> attachments;

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
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
