package com.pelusapets.service_venta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelusapets.service_venta.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long>{

  Optional<Carrito> findByUsuarioId(Long usuarioId);
}
