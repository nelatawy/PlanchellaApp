package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockCommunityRepository implements ICommunityRepository {

    @Override
    public List<Community> getCommunities(int count, Long user_id) {
        List<Community> communities = new ArrayList<>();
        for (int i = 0; i < count/2; i++) {
            communities.add(new Community());

            communities.add(new Community());
        }
        if (count % 2 == 0) {
            communities.add(new Community());
        }
        return communities;
    }

    @Override
    public Community getCommunity(Long community_id) {
        return new Community(
                community_id,
                "CSED");
    }

    @Override
    public void saveCommunity(Community community) {
        return;
    }

    @Override
    public void deleteCommunity(Long community_id){
        return;
    }

}
