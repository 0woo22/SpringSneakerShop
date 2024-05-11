package com.github.springsneaker.repository.sneakerModelTraits;

import com.github.springsneaker.repository.sneaker.Sneaker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "sneaker_model_traits")
public class SneakerModelTrait {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_trait_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private Sneaker model;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "trait_id", nullable = false)
    private SneakerTrait trait;

}