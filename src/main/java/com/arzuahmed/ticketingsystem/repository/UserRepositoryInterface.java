package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {

     List<User> findUserByEmail(@Email String email);
     Optional<User> findByEmail(String email);
     List<User> findUserByUserName(String userName);

//

    // Optional<User> findByUserNameAndPassword(String password, String userName);

}
