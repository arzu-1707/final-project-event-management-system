package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {

     List<User> findUserByEmail(@Email String email);

   @EntityGraph(attributePaths = "roles")   //Security-de exception atirdi lazy yuklenme ile bagli ona gore bu annotasiya elave edildi
     Optional<User> findByEmail(String email);

    Page<User> findAllByUserName(@NotEmpty @NotNull @NotBlank String userName, Pageable pageable);

    @EntityGraph(attributePaths = "roles")
    User findUsersByEmail(@Email @NotBlank String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tickets WHERE u.id = :id")
    User findByIdWithTickets(@Param("id") Long id);



//

    // Optional<User> findByUserNameAndPassword(String password, String userName);

}
