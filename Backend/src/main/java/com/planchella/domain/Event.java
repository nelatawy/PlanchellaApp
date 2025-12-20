package com.planchella.domain;

import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
public class Event {

    private Long id;

    @Setter
    private EventType eventType;

    private EventSize eventSize;
    private Long author_id;
    private Long community_id;

    @Setter
    private String title;

    @Setter
    private String description;
    private String creationDate;

    private List<AttachmentMetadata> attachments;

    public Event(Long id, EventType eventType, EventSize eventSize, Long author_id, Long community_id,
            String title, String description,
            String creationDate, List<AttachmentMetadata> attachments) {
        this.id = id;
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.title = title;
        this.author_id = author_id;
        this.description = description;
        this.creationDate = creationDate;
        this.community_id = community_id;
        this.attachments = attachments;
    }

    public Event() {
        this.title = "";
    }

    public void updateByDelta(Event event) {
        this.eventType = event.eventType != null ? event.eventType : this.eventType;
        this.eventSize = event.eventSize != null ? event.eventSize : this.eventSize;
        this.title = event.title != null ? event.title : this.title;
        this.author_id = event.author_id != null ? event.author_id : this.author_id;
        this.description = event.description != null ? event.description : this.description;
        this.creationDate = event.creationDate != null ? event.creationDate : this.creationDate;
        this.community_id = event.community_id != null ? event.community_id : this.community_id;
        this.attachments = event.attachments != null ? event.attachments : this.attachments;
    }

    public static Event getMockData() {
        return new Event(4L,
                EventType.HACKATHON,
                EventSize.LARGE,
                4L,
                1L,
                "sample test",
                "Description",
                new Date().toString(),
                List.of(new AttachmentMetadata()));
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
