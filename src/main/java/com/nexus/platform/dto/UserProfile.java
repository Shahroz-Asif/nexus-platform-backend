package com.nexus.platform.dto;

import com.nexus.platform.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfile {
    private String name;
    private String email;
    private String bio;
    private String portfolio;
    private String preferences;
    private String avatarUrl;
    private LocalDateTime createdAt;


    public static UserProfile fromUser(User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(user.getName());
        userProfile.setEmail(user.getEmail());
        userProfile.setBio(user.getBio());
        userProfile.setPortfolio(user.getPortfolio());
        userProfile.setPreferences(user.getPreferences());
        userProfile.setAvatarUrl(user.getAvatarUrl());
        userProfile.setCreatedAt(user.getCreatedAt());
        return userProfile;
    }
}
