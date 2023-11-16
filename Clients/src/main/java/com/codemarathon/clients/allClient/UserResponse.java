package com.codemarathon.clients.allClient;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String responseCode;
    private String message;
    private Object details;
}
