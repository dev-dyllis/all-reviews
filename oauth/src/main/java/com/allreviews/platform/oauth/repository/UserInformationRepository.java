package com.allreviews.platform.oauth.repository;

import com.allreviews.platform.oauth.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationRepository extends JpaRepository<UserAuthentication, Long> {
    Optional<UserAuthentication> findByEmail(String email);
}
