package com.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Silla {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@OneToMany(mappedBy = "silla", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
private List<Reserva> reservaList;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_estadio")
@JsonIgnore
private Estadio estadio;

}
