package com.planchella.repositories.communities;

import com.planchella.domain.CommunityData;

import java.util.ArrayList;
import java.util.List;

public class MockCommunityRepository implements ICommunityRepository {

    @Override
    public List<CommunityData> getCommunities(int count, String username) {
        List<CommunityData> communities = new ArrayList<>();
        for (int i = 0; i < count/2; i++) {
            communities.add(new CommunityData("CSED", 99));
            communities.add(new CommunityData("WATERLOO", 81));
        }
        if (count % 2 == 0) {
            communities.add(new CommunityData("CSED", 99));
        }
        return communities;
    }
}
