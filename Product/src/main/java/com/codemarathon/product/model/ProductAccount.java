package com.codemarathon.product.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Product-Account")
public class ProductAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private String accountBank;
    private String accountNumber;
    private String businessName;
    private String businessEmail;
    private String businessContact;
    private String businessContactMobile;
    private String businessMobile;
    private String country;
}
