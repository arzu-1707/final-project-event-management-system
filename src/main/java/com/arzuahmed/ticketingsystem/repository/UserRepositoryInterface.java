package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
    boolean existsUserByName(String name);
}
