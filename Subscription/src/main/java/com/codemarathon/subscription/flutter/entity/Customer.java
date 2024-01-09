package com.codemarathon.subscription.flutter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Column(name = "id", insertable = false, updatable = false)
    private Long id;
    @Column(name = "fullname")
    private String fullname;
    private String phone_number;
    private String email;
    private String created_at;
}
