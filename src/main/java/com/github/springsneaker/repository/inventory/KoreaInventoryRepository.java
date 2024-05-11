package com.github.springsneaker.repository.inventory;

import com.github.springsneaker.repository.inventory.KoreaInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KoreaInventoryRepository extends JpaRepository<KoreaInventory, Integer> {
}