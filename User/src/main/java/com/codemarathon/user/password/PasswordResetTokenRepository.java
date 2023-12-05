package com.codemarathon.user.password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long>{
    PasswordResetToken findByToken(String passwordToken);
}