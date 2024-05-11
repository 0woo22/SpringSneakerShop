package com.github.springsneaker.repository.generalUser;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneralUserRepository extends JpaRepository<GeneralUser, Integer> {

    Optional<GeneralUser> findGeneralUserByUserId(Integer userId);
}