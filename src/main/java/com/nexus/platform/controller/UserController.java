package com.nexus.platform.controller;

import com.nexus.platform.dto.UserProfile;
import com.nexus.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('INVESTOR') or hasRole('ENTREPRENEUR')")
    public ResponseEntity<UserProfile> getUserProfile(Principal principal) {
        return ResponseEntity.ok(userService.getUserProfile(principal.getName()));
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("hasRole('INVESTOR') or hasRole('ENTREPRENEUR')")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfileById(id));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('INVESTOR') or hasRole('ENTREPRENEUR')")
    public ResponseEntity<?> updateUserProfile(Principal principal, @RequestBody UserProfile userProfile) {
        return ResponseEntity.ok(userService.updateUserProfile(principal.getName(), userProfile));
    }
}
