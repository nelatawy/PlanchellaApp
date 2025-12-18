package com.planchella.Configs;

import com.planchella.repositories.communities.DBCommunityRepository;
import com.planchella.repositories.communities.ICommunityRepository;
import com.planchella.repositories.events.DBEventRepository;
import com.planchella.repositories.events.IEventRepository;
import com.planchella.repositories.users.DBUserRepository;
import com.planchella.repositories.users.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ICommunityRepository communityRepo() {
        return new DBCommunityRepository();
    }

    @Bean
    public IEventRepository eventRepo() {
        return new DBEventRepository();
    }

    @Bean
    public IUserRepository userRepo() {
        return new DBUserRepository();
    }
}
