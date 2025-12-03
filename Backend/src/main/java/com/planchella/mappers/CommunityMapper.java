package com.planchella.mappers;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.domain.Community;
import com.planchella.domain.Membership;
import com.planchella.entities.CommunityEntity;
import com.planchella.entities.MembershipEntity;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.List;

public class CommunityMapper {

    public static CommunityEntity domainToEntity(Community community, Session session) {
        CommunityEntity entity = new CommunityEntity();
        entity.setId(community.getId());
        entity.setName(community.getName());

        List<MembershipEntity> memberships = new ArrayList<MembershipEntity>();
        for(Membership membership : community.getMemberships()) {
            MembershipEntity membershipEntity = session.getReference(MembershipEntity.class, membership.getId());
            memberships.add(membershipEntity);
        }
        entity.setMemberships(memberships);
        return entity;
    }

    public static Community entityToDomain(CommunityEntity entity) {
        Community community = new Community(
                entity.getId(),
                entity.getName()
        );
        for (MembershipEntity membershipEntity : entity.getMemberships()) {
            community.addMembership(membershipEntity.getId(), membershipEntity.getUser().getId(), membershipEntity.getType());
        }
        return community;
    }

    public static CommunityDTO domainToDTO(Community community) {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.id = community.getId();
        communityDTO.name = community.getName();
        return communityDTO;
    }

    public static Community DTOtoDomain(CommunityDTO communityDTO) {

        return new Community(
                communityDTO.id,
                communityDTO.name
        );
    }

}
