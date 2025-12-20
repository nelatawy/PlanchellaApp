package com.planchella.repositories.memberships;

import com.planchella.domain.Membership;
import org.springframework.stereotype.Repository;

@Repository
public interface IMembershipRepository {
    Membership getMembership(Long membershipId);

    Membership getMembership(Long userId, Long communityId);

    java.util.List<Membership> getMembershipsByUser(Long userId);

    java.util.List<Membership> getMembershipsByCommunity(Long communityId);

    void saveMembership(Membership membership);
}
