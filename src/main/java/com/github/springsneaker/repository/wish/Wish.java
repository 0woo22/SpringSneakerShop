package com.github.springsneaker.repository.wish;

import com.github.springsneaker.repository.sneaker.Sneaker;
import com.github.springsneaker.repository.generalUser.GeneralUser;
import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "wish")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "g_user_id", nullable = false)
    private GeneralUser gUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private Sneaker model;

    @Column(name = "sneaker_size")
    private Integer sneakerSize;

    @Column(name = "expected_replenisment_date")
    private LocalDateTime expectedReplenismentDate;

    @Column(name = "wish_at", nullable = false)
    private LocalDateTime wishAt;

}