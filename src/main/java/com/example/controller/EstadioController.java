package com.example.controller;


import com.example.entity.Estadio;
import com.example.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/estadio")
public class EstadioController {

    @Autowired
    private EstadioRepository estadioRepository;

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(estadioRepository.findAll());
    }

    @GetMapping("/get")
    public ResponseEntity<Estadio> findById(){
        return ResponseEntity.ok(estadioRepository.findById(3L).orElseThrow());
    }


}
