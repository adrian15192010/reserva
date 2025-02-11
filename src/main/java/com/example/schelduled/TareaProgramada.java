package com.example.schelduled;

import com.example.entity.Evento;
import com.example.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TareaProgramada {

    @Autowired
    private EventoRepository eventoRepository;

    @Scheduled(fixedRate = 30000)
    public void ejecutarTarea(){

        LocalDateTime now = LocalDateTime.now();
        List<Evento> list = eventoRepository.findAll();

        for (Evento evento : list){
            LocalDateTime inicio = evento.getInicio().minusMinutes(10);

            if(inicio.isBefore(now)){
                evento.setCulminado(true);
                eventoRepository.save(evento);
            }
            System.out.println(inicio);
        }

    }

}
