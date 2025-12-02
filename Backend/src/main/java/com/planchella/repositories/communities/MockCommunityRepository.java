package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import com.planchella.domain.CommunityData;

import java.util.ArrayList;
import java.util.List;

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
        Community community = new Community();
        community.setId(community_id);
        community.setName("CSED");
        return community;
    }

    @Override
    public void updateCommunity(Long community_id, Community community) {
        return;
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
