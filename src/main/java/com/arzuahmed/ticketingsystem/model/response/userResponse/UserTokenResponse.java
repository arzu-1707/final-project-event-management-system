package com.arzuahmed.ticketingsystem.model.response.userResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenResponse {
    private String accessToken;
    private String refreshToken;
}
