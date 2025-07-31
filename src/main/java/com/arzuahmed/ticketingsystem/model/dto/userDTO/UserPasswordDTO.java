package com.arzuahmed.ticketingsystem.model.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
