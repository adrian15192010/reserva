package com.example.repository;


import com.example.entity.Silla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SillaRepository extends JpaRepository<Silla, Long> {
}
