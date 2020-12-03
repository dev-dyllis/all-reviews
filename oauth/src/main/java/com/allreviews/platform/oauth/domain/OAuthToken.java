package com.allreviews.platform.oauth.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthToken {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;
    private String scope;
}
