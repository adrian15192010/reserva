package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Estadio {

    @Id
    private Long id;

    private String nombre;
    
    private String direccion;

    private int capacidad;

    @OneToMany(mappedBy = "estadio", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Silla> sillaList;

    @OneToMany(mappedBy = "estadio", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Evento> eventoList;

}
