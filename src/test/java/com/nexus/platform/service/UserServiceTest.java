package com.nexus.platform.service;

import com.nexus.platform.dto.UserProfile;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.User;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().email("test@example.com").password("password").role(Role.INVESTOR).build();
        user.setId(1L);
        user.setBio("Test bio");
    }

    @Test
    void testGetUserProfile_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserProfile foundProfile = userService.getUserProfile("test@example.com");

        assertNotNull(foundProfile);
        assertEquals(user.getEmail(), foundProfile.getEmail());
        assertEquals(user.getBio(), foundProfile.getBio());
    }

    @Test
    void testGetUserProfile_NotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserProfile("nonexistent@example.com"));
    }

    @Test
    void testUpdateUserProfile() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserProfile updatedProfile = new UserProfile();
        updatedProfile.setBio("Updated bio");

        User updatedUser = userService.updateUserProfile("test@example.com", updatedProfile);

        assertNotNull(updatedUser);
        assertEquals("Updated bio", updatedUser.getBio());
    }
}
