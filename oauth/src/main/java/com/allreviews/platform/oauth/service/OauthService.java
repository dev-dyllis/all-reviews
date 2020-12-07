package com.allreviews.platform.oauth.service;

import com.allreviews.platform.oauth.domain.UserAuthentication;
import com.allreviews.platform.oauth.domain.UserToken;
import com.allreviews.platform.oauth.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;

    private final UserTokenRepository userTokenRepository;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${oauth.grant-type}")
    private String grantType;

    public Object login(UserAuthentication user) {
        String oauthUrl = "http://localhost:8101/oauth/token";
        String email = user.getEmail();
        String password = user.getPassword();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("username", email);
        params.add("password", password);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity = null;
        Map<String, Object> result = null;
        try {
            responseEntity = restTemplate.postForEntity(oauthUrl, requestEntity, Map.class);
            result = responseEntity.getBody();

            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 로그인 성공시 MySQL/Redis 토큰 저장
                String accessToken = result.get("access_token").toString();
                String userId = new Jackson2JsonParser().parseMap(JwtHelper.decode(accessToken).getClaims()).get("user_id").toString();

                UserToken userToken = UserToken.builder()
                        .userId(Long.parseLong(userId))
                        .accessToken(accessToken)
                        .refreshToken(result.get("refresh_token").toString())
                        .tokenType(result.get("token_type").toString())
                        .expiresIn(Long.parseLong(result.get("expires_in").toString()))
                        .scope(result.get("scope").toString())
                        .build();

                userTokenRepository.save(userToken);

                ValueOperations<String, Object> value = redisTemplate.opsForValue();
                value.set(userToken.getAccessToken(), userToken);

                return userToken;
            } else {
                return statusCode;
            }
        } catch(Exception e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    public String logout() {
        return "success";
    }
}
