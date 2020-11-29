package com.allreviews.platform.oauth.domain;

import lombok.Data;

@Data
public class OAuthToken {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private long expiresIn;
    private String scope;
}
