package com.github.springsneaker.repository.shipping;

import com.github.springsneaker.repository.orders.Order;
import com.github.springsneaker.repository.sneaker.Sneaker;
import com.github.springsneaker.repository.user.AdminUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminUser admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private Sneaker model;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "shipping_address", nullable = false, length = 30)
    private String shippingAddress;

    @Column(name = "shipping_at", nullable = false)
    private Instant shippingAt;

}