package com.allreviews.platform.oauth.controller;

import com.allreviews.platform.oauth.domain.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {

    private final RestTemplate restTemplate;

    @GetMapping("/callback")
    public OAuthToken callbackSocial(@RequestParam String code) {
        String credentials = "testClientId:testSecret";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:8101/oauth2/callback");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<OAuthToken> response = restTemplate.postForEntity("http://localhost:8101/oauth/token", request, OAuthToken.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }
}
