package com.example.jwt;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
