package com.pelusapets.service_venta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelusapets.service_venta.model.Orden;

@Repository
public interface OrdenRepository extends  JpaRepository<Orden, Long> {

  List<Orden> findByUsuarioId(Long usuarioId);

  Optional<Orden> findByNumeroDeOrden(String numeroDeOrden);

  Page<Orden> findByUsuarioId(Long usuarioId, Pageable pageable);

  List<Orden> findByEstado(String estado);

  List<Orden> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}
