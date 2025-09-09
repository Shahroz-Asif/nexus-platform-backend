package com.nexus.platform.service;

import com.nexus.platform.dto.LoginRequest;
import com.nexus.platform.dto.UserRegistrationRequest;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.User;
import com.nexus.platform.repository.UserRepository;
import com.nexus.platform.security.JwtTokenUtil;
import com.nexus.platform.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthService authService;

    private UserRegistrationRequest registrationRequest;
    private User user;
    private LoginRequest loginRequest;
    @Mock
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        registrationRequest = new UserRegistrationRequest();
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("password");
        registrationRequest.setRole(Role.INVESTOR);

        user = User.builder().email("test@example.com").password("encodedPassword").role(Role.INVESTOR).build();

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");
    }

    @Test
    void testRegister() {
        when(passwordEncoder.encode(registrationRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = authService.register(registrationRequest);

        assertNotNull(registeredUser);
        assertEquals(registrationRequest.getEmail(), registeredUser.getEmail());
        assertEquals("encodedPassword", registeredUser.getPassword());
        assertEquals(Role.INVESTOR, registeredUser.getRole());
    }

    @Test
    void testLogin_Success() {
        when(userDetailsService.loadUserByUsername(loginRequest.getEmail())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("test_token");

        String token = authService.login(loginRequest);

        assertNotNull(token);
        assertEquals("test_token", token);
    }

    @Test
    void testLogin_Failure() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
    }
}
