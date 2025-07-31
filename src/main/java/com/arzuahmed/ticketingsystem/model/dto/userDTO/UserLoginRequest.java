package com.arzuahmed.ticketingsystem.model.dto.userDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @Email
    @NotBlank
    @NotBlank
    @NotEmpty
    private String email;

    @Size(min = 8)
    @NotEmpty
    @NotNull
    @NotEmpty
    private String password;
}
