package com.example.jwt;

public record AuthRequest(
        String email,
        String password
) {
}
