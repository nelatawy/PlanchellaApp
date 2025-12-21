package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockCommunityRepository implements ICommunityRepository {

    @Override
    public List<Community> getCommunitiesByAuthor(int count, Long userId) {
        List<Community> communities = new ArrayList<>();
        for (int i = 0; i < count / 2; i++) {
            communities.add(new Community());

            communities.add(new Community());
        }
        if (count % 2 == 0) {
            communities.add(new Community());
        }
        return communities;
    }

    @Override
    public Community getCommunity(Long communityId) {
        return new Community(
                communityId,
                "Demo Community",
                "This is a demo community description.",
                new java.util.Date().toString());
    }

    @Override
    public void saveCommunity(Community community) {
        return;
    }

    @Override
    public void deleteCommunity(Long communityId) {
        return;
    }

    @Override
    public List<Community> SearchCommunities(String keywords, int count, int offset) {
        return List.of();
    }

}
