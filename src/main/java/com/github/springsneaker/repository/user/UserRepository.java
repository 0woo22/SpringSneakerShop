package com.github.springsneaker.repository.user;

import com.github.springsneaker.repository.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}