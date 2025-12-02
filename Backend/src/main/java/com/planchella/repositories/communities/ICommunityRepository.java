package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import com.planchella.domain.CommunityData;

import java.util.List;

public interface ICommunityRepository {

    List<Community> getCommunities(int count, Long user_id);

    Community getCommunity(Long community_id);

    void updateCommunity(Long community_id, Community community);

    void saveCommunity(Community community);

    void deleteCommunity(Long community_id);
}
