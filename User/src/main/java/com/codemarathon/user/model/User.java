package com.codemarathon.user.model;

import com.codemarathon.user.constants.Role;
import com.codemarathon.user.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;



@Entity
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Master_sub_user_Tbl")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "master_sub_user_seq",sequenceName = "master_sub_user_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String subscriptionCode;
    @NaturalId
    private String email;
    private String address;
    private String mobile;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String registeredTime;
    private boolean isVerified = false;

    @OneToMany(mappedBy = "user")
    private List<Token>tokens;

    private String productId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
