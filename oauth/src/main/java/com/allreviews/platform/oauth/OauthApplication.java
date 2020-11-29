package com.allreviews.platform.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OauthApplication {
    public static void main(String[] args) {
        //PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //System.out.printf("testSecret : %s\n", passwordEncoder.encode("1234"));
        SpringApplication.run(OauthApplication.class, args);
    }
}
