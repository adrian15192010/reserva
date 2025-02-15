package com.example.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("habilitado_is")
        Boolean habilitado
) {
}
