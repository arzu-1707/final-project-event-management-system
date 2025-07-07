package com.arzuahmed.ticketingsystem.model.dto.userDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    private String password;

    private String role;

}
