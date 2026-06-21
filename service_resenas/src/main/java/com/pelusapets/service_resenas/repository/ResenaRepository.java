package com.pelusapets.service_resenas.repository;

import com.pelusapets.service_resenas.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
}