package com.example.controller;


import com.example.entity.Evento;
import com.example.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;



    @PostMapping("/create")
    public ResponseEntity<?> crearEvento(@RequestBody Evento nuevoEvento) throws Exception {

        nuevoEvento.setCulminado(false);

        if (nuevoEvento.getInicio().isBefore(LocalDateTime.now()) || nuevoEvento.getInicio().isAfter(nuevoEvento.getFin()))
            return ResponseEntity.badRequest().build();


        List<Evento> eventosExistentes = eventoRepository.findAll();

        for (Evento evento : eventosExistentes) {

            if (nuevoEvento.getInicio().isBefore(evento.getFin()) && nuevoEvento.getFin().isAfter(evento.getInicio())) {
                throw new RuntimeException(" Ya hay un evento programado en este horario y estadio");
            }
        }
        eventoRepository.save(nuevoEvento);

        return  ResponseEntity.ok(eventosExistentes);

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
