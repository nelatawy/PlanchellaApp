package com.planchella.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.domain.AttachmentMetadata;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {
    public Long id;
    public EventType eventType;
    public EventSize eventSize;
    public Long authorId;
    public Long communityId;
    public String title;

    public String description;
    public String creationDate;

    public Long upvoteCount;
    public Long downvoteCount;
    public String eventStartDate;
    public String eventEndDate;

    // list of attachment
    public List<AttachmentMetadata> attachments;

    public EventDTO(Long id, EventType eventType, EventSize eventSize, Long author_id, Long community_id, String title,
            String description, String creationDate, Long upvoteCount, Long downvoteCount, String eventStartDate,
            String eventEndDate, List<AttachmentMetadata> attachments) {
        this.id = id;
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.authorId = author_id;
        this.communityId = community_id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.attachments = attachments;
    }

    public EventDTO() {
    }
}
