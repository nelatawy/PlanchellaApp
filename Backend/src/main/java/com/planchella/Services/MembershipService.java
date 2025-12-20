package com.planchella.Services;

import com.planchella.domain.Membership;
import com.planchella.repositories.memberships.IMembershipRepository;
import com.planchella.repositories.memberships.DBMembershipRepository;

public class MembershipService {
    private final IMembershipRepository membershipRepository;

    public MembershipService() {
        // In a real Spring app, this would be autowired.
        // For now, consistent with other code likely manually instantiating or if using
        // a DI framework not shown.
        // Assuming manual instantiation for now based on looking at potential lack of
        // @Service/@Autowired usage in snippets seen (DBMembershipRepository creates
        // its own session factory).
        this.membershipRepository = new DBMembershipRepository();
    }

    // Constructor for DI if available
    public MembershipService(IMembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Membership getMembership(Long membershipId) {
        return membershipRepository.getMembership(membershipId);
    }

    public void saveMembership(Membership membership) {
        membershipRepository.saveMembership(membership);
    }
}
