package com.planchella.mappers;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.domain.Community;
import com.planchella.domain.Membership;
import com.planchella.entities.CommunityEntity;
import com.planchella.entities.MembershipEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class CommunityMapper {
    private final SessionFactory factory;

    public CommunityMapper(SessionFactory factory) {
        this.factory = factory;
    }

    public CommunityEntity domainToEntity(Community community) {
        Session session = factory.openSession();
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

    public Community entityToDomain(CommunityEntity entity) {
        Community community = new Community();
        community.setId(entity.getId());
        community.setName(entity.getName());
        List<Membership> memberships = new ArrayList<>();
        for (MembershipEntity membershipEntity : entity.getMemberships()) {
            Membership membership = new Membership(
                    membershipEntity.getId(),
                    membershipEntity.getUser().getId(),
                    membershipEntity.getCommunity().getId(),
                    membershipEntity.getType());

            memberships.add(membership);
        }
        community.setMemberships(memberships);
        return community;
    }

    public CommunityDTO domainToDTO(Community community) {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.id = community.getId();
        communityDTO.name = community.getName();
        return communityDTO;
    }

    public Community DTOtoEntity(CommunityDTO communityDTO) {
        Community community = new Community();
        community.setId(communityDTO.id);
        community.setName(communityDTO.name);
        return community;
    }

}
