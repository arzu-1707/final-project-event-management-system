package com.arzuahmed.ticketingsystem.model.response.userResponse;

import com.arzuahmed.ticketingsystem.model.response.roleResponse.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String userName;

    private String email;

    private Set<RoleResponse> roles;

}
