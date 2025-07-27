package com.arzuahmed.ticketingsystem.model.response.userResponse;

import com.arzuahmed.ticketingsystem.model.entity.Role;
import com.arzuahmed.ticketingsystem.model.response.roleResponse.RoleResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {

    private Long id;
    private String userName;

    private String email;

    private Set<RoleResponse> roles;

}
