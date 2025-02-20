package com.example.controller;

import com.example.dto.ReservaDto;
import com.example.entity.Evento;
import com.example.entity.Reserva;
import com.example.entity.Silla;
import com.example.repository.EventoRepository;
import com.example.repository.ReservaRepository;
import com.example.repository.SillaRepository;
import com.example.service.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/reserva")
public class ReservaController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private SillaRepository sillaRepository;

    @Autowired
    private ReservaServicio reservaServicio;


    @PostMapping("/create/{idEvento}/{idSilla}")
    public ResponseEntity<?> createOne(
            @RequestBody Reserva reserva,@PathVariable Long idEvento, @PathVariable Long idSilla){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaServicio.createReserva(reserva, idEvento, idSilla));
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<?> reservaDTOget(@PathVariable Long id){

        Reserva reserva = reservaRepository.findById(id).orElseThrow();

        return ResponseEntity.ok(ReservaDto.builder()
                .id(reserva.getId())
                .usuario(reserva.getUsuario())
                .eventoName(reserva.getEvento().getNombre())
                .sillaId(reserva.getId())
                .build());

    }


}
