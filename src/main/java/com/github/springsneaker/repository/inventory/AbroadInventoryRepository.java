package com.github.springsneaker.repository.inventory;



import com.github.springsneaker.repository.inventory.AbroadInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbroadInventoryRepository extends JpaRepository<AbroadInventory, Integer> {
}