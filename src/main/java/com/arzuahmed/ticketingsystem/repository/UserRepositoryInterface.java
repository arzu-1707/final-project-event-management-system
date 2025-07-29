package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {

     List<User> findUserByEmail(@Email String email);
     Optional<User> findByEmail(String email);
     List<User> findUserByUserName(String userName);
    Page<User> findAllByUserName(@NotEmpty @NotNull @NotBlank String userName, Pageable pageable);

//

    // Optional<User> findByUserNameAndPassword(String password, String userName);

}
