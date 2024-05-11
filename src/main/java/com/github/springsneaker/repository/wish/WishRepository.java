package com.github.springsneaker.repository.wish;


import com.github.springsneaker.repository.wish.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Integer> {
}