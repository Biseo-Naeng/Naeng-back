package com.naeng_biseo.naeng_biseo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
