package com.codemarathon.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Master_Sub_Product")
public class Product {
    @Id
    @SequenceGenerator(name = "master_sub_product_seq",sequenceName = "master_sub_product_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String productCode;
    private String productDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Plan> plans;
    private String dateCreated;
    private String dateUpdated;
    private boolean isUpdated;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Like> likes;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SubscribedUser> subscribedUsers;
}
