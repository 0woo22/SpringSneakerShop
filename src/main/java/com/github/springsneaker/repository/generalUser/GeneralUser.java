package com.github.springsneaker.repository.generalUser;


import com.github.springsneaker.repository.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "general_user")
public class GeneralUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_user_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "favortie_shopping_address", length = 30)
    private String favortieShoppingAddress;

    @Column(name = "my_card_num", length = 20)
    private String myCardNum;

    @Column(name = "my_bank_account", length = 30)
    private String myBankAccount;

}