package com.codemarathon.product.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserComment {
    @Id
    private String id;
    private String text;
    private Date datePosted;
    private Long authorId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany
    private List<Like> likes;
}
