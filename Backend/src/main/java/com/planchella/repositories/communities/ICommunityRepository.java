package com.planchella.repositories.communities;

import com.planchella.domain.CommunityData;

import java.util.List;

public interface ICommunityRepository {
    List<CommunityData> getCommunities(int count, String username);
}
