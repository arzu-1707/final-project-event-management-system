package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {

    List<User> findUserByName(String userName);

    boolean existsUsersByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
