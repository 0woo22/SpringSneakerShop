package com.github.springsneaker.repository.user;


import com.github.springsneaker.repository.generalUser.GeneralUser;
import com.github.springsneaker.repository.user.AdminUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "phone_num", nullable = false, length = 15)
    private String phoneNum;

    @OneToOne(mappedBy = "user")
    private GeneralUser generalUsers;

    @OneToOne(mappedBy = "user")
    private AdminUser adminUsers;

}