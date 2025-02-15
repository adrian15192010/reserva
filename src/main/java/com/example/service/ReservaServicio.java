package com.example.service;

import com.example.entity.Evento;
import com.example.entity.Reserva;
import com.example.entity.Silla;
import com.example.jwt.AuthService;
import com.example.jwt.UserRepository;
import com.example.repository.EventoRepository;
import com.example.repository.ReservaRepository;
import com.example.repository.SillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class ReservaServicio {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private SillaRepository sillaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;


    public Map createReserva (Reserva reserva, Long idEvento, Long idSilla){

        if (!userRepository.findByEmail(authService.getUsername()).get().getHabilitado())
            throw new RuntimeException();

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Evento no encontrado"));


        if (evento.getCulminado()) throw new RuntimeException("el evento ya ha culminado");

        List<Reserva> reservaList = reservaRepository.findByEvento(evento);

        for (Reserva r : reservaList){

            if (r.getSilla().getId() == idSilla)  return Map.of("message", "La silla ya esta reservada para el evento");
                //throw new RuntimeException("La silla ya esta reservada para el evento");

        }

        Silla silla = sillaRepository.findById(idSilla).get();

        reserva.setEvento(evento);
        reserva.setSilla(silla);
        reserva.setInicio(evento.getInicio());
        reserva.setFin(evento.getFin());

        Reserva reserva2 = reservaRepository.save(reserva);

        return Map.of(
                "id", reserva2.getId(),
                "message", "Reserva creada exitosamente"
        );

    }


}
