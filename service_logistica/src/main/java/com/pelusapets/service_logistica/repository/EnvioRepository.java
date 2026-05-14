package com.pelusapets.service_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pelusapets.service_logistica.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
}