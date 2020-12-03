package com.allreviews.platform.oauth.controller;

import com.allreviews.platform.oauth.domain.UserAuthentication;
import com.allreviews.platform.oauth.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OauthService oauthService;

    @PostMapping("/login")
    public Object login(@RequestBody UserAuthentication user) {
        return oauthService.login(user);
    }

    @PostMapping("/logout")
    public String logout() {
        return null;
    }
}
