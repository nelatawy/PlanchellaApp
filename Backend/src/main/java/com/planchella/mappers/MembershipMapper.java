package com.planchella.mappers;

import com.planchella.domain.Membership;
import com.planchella.entities.CommunityEntity;
import com.planchella.entities.MembershipEntity;
import com.planchella.entities.UserEntity;
import org.hibernate.Session;

public class MembershipMapper {

    public static MembershipEntity domainToEntity(Membership membership, Session session) {
        MembershipEntity e = new MembershipEntity();
        e.setId(membership.getId());
        e.setCommunity(session.getReference(CommunityEntity.class, membership.getCommunityId()));
        e.setUser(session.getReference(UserEntity.class, membership.getUserId()));
        e.setType(membership.getType());
        return e;
    }

    public static Membership entityToDomain(MembershipEntity e) {

        return new Membership(e.getId(),
                e.getUser().getId(),
                e.getCommunity().getId(),
                e.getType());
    }

    // public static Membership domainToDTO(Event event){
    // return new EventDTO(event.getId(),
    // event.getEventType(),
    // event.getEventSize(),
    // event.getAuthor_id(),
    // event.getCommunity_id(),
    // event.getTitle(),
    // event.getDescription(),
    // event.getCreationDate(),
    // event.getAttachments()
    // );
    // }
    //
    // public static Event DTOtoDomain(EventDTO e){
    //
    // return new Event(e.id,
    // e.eventType,
    // e.eventSize,
    // e.authorId,
    // e.communityId,
    // e.title,
    // e.description,
    // e.creationDate,
    // e.upvoteCount,
    // e.downvoteCount,
    // e.eventStartDate,
    // e.eventEndDate,
    // e.attachments);
    // }
}
