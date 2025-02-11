package com.example.repository;


import com.example.entity.Estadio;
import com.example.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Long> {

     @Query("SELECT e FROM Evento e WHERE e.estadio = :estadio AND (e.inicio < :fin AND e.fin > :inicio)")
     List<Evento> findByEstadioAndHorario(@Param("estadio") Estadio estadio, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

}
