package com.codemarathon.user.password;

import com.codemarathon.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "master_sub_password_reset_Token")
public class PasswordResetToken {

    @Id
    @SequenceGenerator(name = "master_sub_password_tkn_seq",sequenceName = "master_sub_password_tkn_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private static final int Expiration_Time = 15;


    public PasswordResetToken(String token, User user) {
        super();
        this.token = token;
        this.user =user;
        this.expirationTime = getTokenExpirationTime();
    }

    public PasswordResetToken(String token) {
        super();
        this.token = token;
        this.expirationTime = getTokenExpirationTime();
    }
    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,Expiration_Time);
        return new Date(calendar.getTime().getTime());
    }

}
