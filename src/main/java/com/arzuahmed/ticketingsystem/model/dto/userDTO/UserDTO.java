package com.arzuahmed.ticketingsystem.model.dto.userDTO;

import com.arzuahmed.ticketingsystem.model.dto.roleDTO.RoleDTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    private String password;


}
