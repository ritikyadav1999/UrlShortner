package org.example.urlshortner.controller;

import org.example.urlshortner.security.CustomUserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthContoller {

    @GetMapping("great")
    public String great(@AuthenticationPrincipal CustomUserDetail user) {
        return user.getUser().getPassword();
    }

}
