package com.codemarathon.user.password;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {

    private String email;
    private String newPassword;
    private String confirmPassword;
}
