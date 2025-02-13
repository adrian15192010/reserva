package com.example.controller;

import com.example.dto.DatosEventoAndSillaReservadas;
import com.example.entity.Evento;
import com.example.entity.Reserva;
import com.example.repository.EventoRepository;
import com.example.repository.ReservaRepository;
import com.example.repository.SillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/silla")
public class SillaController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private SillaRepository sillaRepository;

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(sillaRepository.findAll());
    }


    @GetMapping("/reservadas/{idEvento}")
    public ResponseEntity<?> sillasReservadas(@PathVariable Long idEvento){

        Evento evento = eventoRepository.findById(idEvento).get();
        List<Reserva> reservaList = reservaRepository.findByEvento(evento);

        List<Long> listaDeSillasReservadas = new ArrayList<>();

        for (Reserva reserva : reservaList){
            Long silla = reserva.getSilla().getId();
            listaDeSillasReservadas.add(silla);
        }

        return ResponseEntity.ok(DatosEventoAndSillaReservadas.builder()
                .id_evento(evento.getId())
                .nombre(evento.getNombre())
                .tipo(evento.getTipo())
                .culminado(evento.getCulminado())
                .inicio(evento.getInicio())
                .fin(evento.getFin())
                .listaDeSillasReservadas(listaDeSillasReservadas)
                .build()
        );

    }



}
