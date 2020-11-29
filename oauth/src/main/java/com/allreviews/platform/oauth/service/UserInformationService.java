package com.allreviews.platform.oauth.service;

import com.allreviews.platform.oauth.domain.UserInformation;
import com.allreviews.platform.oauth.repository.UserInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInformationService implements UserDetailsService {
    private final UserInformationRepository userInformationRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInformation user = userInformationRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("Email: " + email + " not found!"));
        return user;
    }
}
