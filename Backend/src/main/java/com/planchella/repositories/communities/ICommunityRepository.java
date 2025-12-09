package com.planchella.repositories.communities;

import com.planchella.domain.Community;

import java.util.List;

public interface ICommunityRepository {

    List<Community> getCommunities(int count, Long user_id);

    Community getCommunity(Long community_id);

    void saveCommunity(Community community);

    void deleteCommunity(Long community_id);

}
