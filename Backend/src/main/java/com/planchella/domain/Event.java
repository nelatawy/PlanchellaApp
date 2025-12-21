package com.planchella.domain;

import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Event {

    private Long id;

    private EventType eventType;

    private EventSize eventSize;

    private Long authorId;

    private Long communityId;

    private String title;

    private String description;

    private String creationDate;

    private String expirationDate;

    private Long upvoteCount;

    private Long downvoteCount;

    private boolean hasTime;

    private String eventStartDate;

    private String eventEndDate;

    private boolean hasLocation;

    private String location;

    private List<AttachmentMetadata> attachments;

    public Event(Long id, EventType eventType, EventSize eventSize, Long authorId, Long communityId,
            String title, String description,
            String creationDate, String expirationDate,
            Long upvoteCount, Long downvoteCount,
            boolean hasTime, String eventStartDate, String eventEndDate,
            boolean hasLocation, String location,
            List<AttachmentMetadata> attachments) {
        this.id = id;
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.title = title;
        this.authorId = authorId;
        this.description = description;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.communityId = communityId;
        this.attachments = attachments;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;
        this.hasTime = hasTime;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.hasLocation = hasLocation;
        this.location = location;
    }

    public Event() {
        this.title = "";
    }

    public void updateByDelta(Event event) {
        this.eventType = event.eventType != null ? event.eventType : this.eventType;
        this.eventSize = event.eventSize != null ? event.eventSize : this.eventSize;
        this.title = event.title != null ? event.title : this.title;
        this.description = event.description != null ? event.description : this.description;
        this.attachments = event.attachments != null ? event.attachments : this.attachments;
        this.eventStartDate = event.eventStartDate != null ? event.eventStartDate : this.eventStartDate;
        this.eventEndDate = event.eventEndDate != null ? event.eventEndDate : this.eventEndDate;
    }

    public static Event getMockData() {
        String now = new Date().toString();
        return new Event(4L,
                EventType.HACKATHON,
                EventSize.LARGE,
                4L,
                1L,
                "sample test",
                "Description",
                now,
                now,
                10L,
                2L,
                false,
                new Date().toString(),
                new Date().toString(),
                true,
                null,
                List.of(new AttachmentMetadata()));
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventType=" + eventType +
                ", eventSize=" + eventSize +
                ", authorId=" + authorId +
                ", communityId=" + communityId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", upvoteCount=" + upvoteCount +
                ", downvoteCount=" + downvoteCount +
                ", hasTime=" + hasTime +
                ", eventStartDate='" + eventStartDate + '\'' +
                ", eventEndDate='" + eventEndDate + '\'' +
                ", hasLocation=" + hasLocation +
                ", location='" + location + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
