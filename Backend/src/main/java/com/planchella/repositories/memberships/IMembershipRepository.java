package com.planchella.repositories.memberships;

import com.planchella.domain.Membership;

public interface IMembershipRepository {
    Membership getMembership(Long membershipId);

    void saveMembership(Membership membership);
}
