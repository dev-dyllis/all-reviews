package com.allreviews.platform.oauth.repository;

import com.allreviews.platform.oauth.domain.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {
    Optional<UserInformation> findByEmail(String email);
}
