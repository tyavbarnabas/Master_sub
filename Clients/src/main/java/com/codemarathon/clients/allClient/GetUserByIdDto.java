package com.codemarathon.clients.allClient;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetUserByIdDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String subscriptionCode;
    private String email;
    private String address;
    private String mobile;
}
