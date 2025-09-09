package com.nexus.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.platform.dto.UserProfile;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.User;
import com.nexus.platform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserProfile userProfile;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().email("test@example.com").password("password").role(Role.INVESTOR).build();
        user.setBio("Test Bio");
        userProfile = UserProfile.fromUser(user);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"INVESTOR"})
    void testGetUserProfile() throws Exception {
        when(userService.getUserProfile(anyString())).thenReturn(userProfile);

        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.bio").value("Test Bio"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"INVESTOR"})
    void testUpdateUserProfile() throws Exception {
        when(userService.updateUserProfile(anyString(), any(UserProfile.class))).thenReturn(user);

        mockMvc.perform(put("/users/profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetUserProfile_Unauthorized() throws Exception {
        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isForbidden());
    }
}
