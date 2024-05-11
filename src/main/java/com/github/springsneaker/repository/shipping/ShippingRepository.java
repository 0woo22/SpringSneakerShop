package com.github.springsneaker.repository.shipping;

import com.github.springsneaker.repository.shipping.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
}