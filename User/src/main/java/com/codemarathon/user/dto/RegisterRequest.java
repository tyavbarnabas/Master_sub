package com.codemarathon.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

      private  String firstName;
      private String lastName;
      private String password;
      private String email;
      private String address;
      private String mobile;

}
