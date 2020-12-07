package com.allreviews.platform.oauth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_TOKEN")
public class UserToken implements Serializable {
    @Id
    @Column(name = "USER_ID")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
