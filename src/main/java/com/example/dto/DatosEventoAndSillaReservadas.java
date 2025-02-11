package com.example.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatosEventoAndSillaReservadas {


    private Long id_evento;

    private String nombre;

    private String tipo;

    private Boolean culminado;

    private LocalDateTime inicio;

    private LocalDateTime fin;

    private List<Long> listaDeSillasReservadas;

}
