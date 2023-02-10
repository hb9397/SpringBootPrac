package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.persistence.UserRepository;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("not found user"));
    }
}
