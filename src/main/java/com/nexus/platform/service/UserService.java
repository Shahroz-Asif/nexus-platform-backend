package com.nexus.platform.service;

import com.nexus.platform.dto.UserProfile;
import com.nexus.platform.entity.User;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserProfile getUserProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return UserProfile.fromUser(user);
    }

    public UserProfile getUserProfileById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserProfile.fromUser(user);
    }

    public User updateUserProfile(String email, UserProfile userProfile) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        user.setBio(userProfile.getBio());
        user.setPortfolio(userProfile.getPortfolio());
        user.setPreferences(userProfile.getPreferences());
        return userRepository.save(user);
    }
}
