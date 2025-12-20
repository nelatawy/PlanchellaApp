package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommunityRepository {

    List<Community> getCommunitiesByAuthor(int count, Long userId);

    Community getCommunity(Long communityId);

    void saveCommunity(Community community);

    void deleteCommunity(Long communityId);

}
