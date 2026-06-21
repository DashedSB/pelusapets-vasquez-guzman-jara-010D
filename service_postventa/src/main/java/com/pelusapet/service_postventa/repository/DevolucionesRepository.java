package com.pelusapet.service_postventa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelusapet.service_postventa.model.Devoluciones;

@Repository
public interface DevolucionesRepository extends JpaRepository<Devoluciones, Long> {

  List<Devoluciones> findByUsuarioId(Long usuarioId);

  List<Devoluciones> findByOrdenId(Long ordenId);

  List<Devoluciones> findByEstado(String estado);

  List<Devoluciones> findByUsuarioIdAndEstado(Long usuarioId, String estado);
  
}
