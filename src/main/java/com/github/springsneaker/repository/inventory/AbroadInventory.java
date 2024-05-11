package com.github.springsneaker.repository.inventory;

import com.github.springsneaker.repository.sneaker.Sneaker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "abroad_inventory")
public class AbroadInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "abroad_inventory_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Sneaker model;

    @Column(name = "sneaker_size")
    private Integer sneakerSize;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "expected_delivered_day")
    private Integer expectedDeliveredDay;

}