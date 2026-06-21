package com.pelusapets.service_promociones.repository;

import com.pelusapets.service_promociones.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionRepository extends JpaRepository<Promocion, Long> {
}