package com.example.controller;


import com.example.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        final String response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest request) {
        final TokenResponse response = (TokenResponse) service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication
    ) {
        return service.refreshToken(authentication);
    }

    @PutMapping("habilitar")
    public ResponseEntity<?> habilitar(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication){
        return ResponseEntity.ok(service.habilitarUser(authentication));
    }


    @GetMapping("all")
    public ResponseEntity<?> users(){
        return ResponseEntity.ok(userRepository.findAll());
    }


}
