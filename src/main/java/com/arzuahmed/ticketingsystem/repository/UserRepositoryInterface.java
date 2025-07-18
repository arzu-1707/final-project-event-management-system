package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {

    List<User> findUserByName(String name);
     List<User> findUserByEmail(@Email String email);
    Optional<User> findByEmail(String email);

//

    // Optional<User> findByUserNameAndPassword(String password, String userName);

}
