package com.github.springsneaker.repository.sneaker;


import com.github.springsneaker.repository.inventory.AbroadInventory;
import com.github.springsneaker.repository.inventory.KoreaInventory;
import com.github.springsneaker.repository.sneakerModelTraits.SneakerModelTrait;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sneaker")
public class Sneaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id", nullable = false)
    private Integer id;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "model")
    private Set<AbroadInventory> abraodInventories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "model")
    private Set<KoreaInventory> koreaInventories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
    private Set<SneakerModelTrait> sneakerModelTraits = new LinkedHashSet<>();

}