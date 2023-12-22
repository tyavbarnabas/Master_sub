package com.codemarathon.product.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.List;

@Entity
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Master_Sub_Product")
public class Product {
    @Id
    @SequenceGenerator(name = "master_sub_product_seq",sequenceName = "master_sub_product_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_sub_product_seq")
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String productCode;
    private String productDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
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
