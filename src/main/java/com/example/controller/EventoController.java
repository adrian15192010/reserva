package com.example.controller;


import com.example.entity.Evento;
import com.example.repository.EventoRepository;
import com.example.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;

    @PostMapping("/create")
    public ResponseEntity<?> crearEvento(@RequestBody Evento nuevoEvento){
        return ResponseEntity.ok(eventoService.crearEvento(nuevoEvento));
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(eventoRepository.findAll());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(eventoRepository.findById(id).orElseThrow());
    }


}
