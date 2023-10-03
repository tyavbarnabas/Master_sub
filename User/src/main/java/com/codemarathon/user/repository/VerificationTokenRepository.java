package com.codemarathon.user.repository;

import com.codemarathon.user.event.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
