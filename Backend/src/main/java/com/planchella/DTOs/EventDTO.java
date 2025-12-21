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
    public String customUrl;
    public String creationDate;
    public String expirationDate;

    public Long upvoteCount;
    public Long downvoteCount;

    public boolean hasTime;
    public String eventStartDate;
    public String eventEndDate;

    public boolean hasLocation;
    public String location;

    public boolean isStarred;
    public boolean isUpvoted;
    public boolean isDownVoted;

    // list of attachment
    public List<AttachmentMetadata> attachments;

    public EventDTO(Long id, EventType eventType, EventSize eventSize, Long author_id, Long community_id, String title,
            String description, String customUrl, String creationDate, String expirationDate, Long upvoteCount,
            Long downvoteCount,
            boolean hasTime, String eventStartDate, String eventEndDate,
            boolean hasLocation, String location,
            boolean isStarred, boolean isUpvoted, boolean isDownVoted,
            List<AttachmentMetadata> attachments) {
        this.id = id;
        this.eventType = eventType;
        this.eventSize = eventSize;
        this.authorId = author_id;
        this.communityId = community_id;
        this.title = title;
        this.description = description;
        this.customUrl = customUrl;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;

        this.hasTime = hasTime;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;

        this.hasLocation = hasLocation;
        this.location = location;

        this.isStarred = isStarred;
        this.isUpvoted = isUpvoted;
        this.isDownVoted = isDownVoted;

        this.attachments = attachments;
    }

    public EventDTO() {
    }
}
