package com.planchella.fetcher.communities;

import com.planchella.models.CommunityData;

import java.util.List;

public interface ICommunityFetcher {
    List<CommunityData> getCommunities(int count, String username);

}
