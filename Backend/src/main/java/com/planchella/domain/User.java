package com.planchella.domain;

import java.util.ArrayList;
import java.util.Objects;

import com.planchella.enums.MembershipType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    private String name;

    private String email;

    private String picUrl;

    private String accountUrl;

    // Simple constructor
    public User(Long id, String name, String email, String picUrl, String accountUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picUrl = picUrl;
        this.accountUrl = accountUrl;
    }

    public User() {
    }

    public static User getMockData() {
        return new User(1L,
                "John Smith",
                "john@example.com",
                "www.pixabay.com/lol",
                "www.planchella.com/users/1");
    }

    public void updateByDelta(User newUserData) {
        if (newUserData != null) {
            if (newUserData.getName() != null)
                this.setName(newUserData.getName());
            if (newUserData.getEmail() != null)
                this.setEmail(newUserData.getEmail());
            if (newUserData.getPicUrl() != null)
                this.setPicUrl(newUserData.getPicUrl());
            if (newUserData.getAccountUrl() != null)
                this.setAccountUrl(newUserData.getAccountUrl());
        }

    }

    // public void setPicUrl(String newPicUrl) {
    // if (newPicUrl == null || newPicUrl.isBlank()) {
    // throw new IllegalArgumentException("Profile Picture URL cannot be null or
    // blank");
    // }
    // this.picUrl = newPicUrl;
    // }
    //
    // public void setAccountUrl(String newAccountUrl) {
    // if (newAccountUrl == null || newAccountUrl.isBlank()) {
    // throw new IllegalArgumentException("Profile Picture URL cannot be null or
    // blank");
    // }
    // this.accountUrl = newAccountUrl;
    // }

    // User is now a pure POJO - no service dependencies
    // All membership-related operations moved to MembershipService

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accountUrl='" + accountUrl + '\'' +
                '}';
    }
}
