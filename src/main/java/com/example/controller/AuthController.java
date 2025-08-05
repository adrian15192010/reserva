package com.example.controller;


import com.example.jwt.*;
import com.example.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        final String response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        return service.authenticate(request);
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

    @PostMapping("/redireccion/{email}")
    public ResponseEntity<String> redireccion(@PathVariable String email){
        return ResponseEntity.ok(service.redireccion(email));
    }

    @PostMapping("/clave/nueva/{clave}")
    public ResponseEntity<String> nuevaClave(@PathVariable String clave){
        return ResponseEntity.ok(service.claveNueva(clave));
    }


    @GetMapping("all")
    public ResponseEntity<?> users(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/eventos")
    public ResponseEntity<?> EliminarTodosLosEventos(){
        eventoRepository.deleteAll();
        return ResponseEntity.ok(Map.of("message", "Eventos eliminados"));
    }

}
