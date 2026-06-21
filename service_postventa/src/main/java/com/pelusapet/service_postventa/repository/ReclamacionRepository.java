package com.pelusapet.service_postventa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelusapet.service_postventa.model.Reclamacion;

@Repository
public interface ReclamacionRepository extends JpaRepository<Reclamacion, Long> {

  List<Reclamacion> findByUsuarioId(Long usuarioId);

  List<Reclamacion> findByEstado(String estado);

  List<Reclamacion> findByPrioridad(String prioridad);

  List<Reclamacion> findByUsuarioIdAndEstado(Long usuarioId, String estado);

}
