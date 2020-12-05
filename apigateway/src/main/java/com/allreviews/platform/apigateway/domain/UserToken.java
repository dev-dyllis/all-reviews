package com.allreviews.platform.apigateway.domain;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken implements Serializable {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private String scope;
}
