package com.codemarathon.clients.allClient.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetUserByIdResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String subscriptionCode;
    private String email;
    private String address;
    private String mobile;
}
