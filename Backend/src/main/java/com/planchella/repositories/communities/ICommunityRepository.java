package com.planchella.repositories.communities;

import com.planchella.domain.Community;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommunityRepository {

    List<Community> getCommunitiesByAuthor(int count, Long user_id);

    Community getCommunity(Long community_id);

    void saveCommunity(Community community);

    void deleteCommunity(Long community_id);

}
