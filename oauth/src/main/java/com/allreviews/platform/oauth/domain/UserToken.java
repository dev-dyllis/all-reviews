package com.allreviews.platform.oauth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_TOKEN")
public class UserToken implements Serializable {
    @Id
    @Column(name = "USER_ID")
    @JsonIgnore
    private Long userId;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "TOKEN_TYPE")
    private String tokenType;

    @Column(name = "EXPIRES_IN")
    private Long expiresIn;

    @Column(name = "SCOPE")
    private String scope;
}
