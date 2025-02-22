package com.example.service;

import com.example.entity.Evento;
import com.example.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EventoService {

    @Autowired
    EventoRepository eventoRepository;


    public Map crearEvento(Evento nuevoEvento){

        nuevoEvento.setCulminado(false);

        if (nuevoEvento.getInicio().isBefore(LocalDateTime.now()) || nuevoEvento.getInicio().isAfter(nuevoEvento.getFin()))
            throw new RuntimeException();


        List<Evento> eventosExistentes = eventoRepository.findAll();

        for (Evento evento : eventosExistentes) {

            if (nuevoEvento.getInicio().isBefore(evento.getFin()) && nuevoEvento.getFin().isAfter(evento.getInicio())) {
                return Map.of("message","Ya hay un evento programado en este horario y estadio");
            }
        }
        eventoRepository.save(nuevoEvento);

        return  Map.of("message","evento creado exitosamente");


    }


}
