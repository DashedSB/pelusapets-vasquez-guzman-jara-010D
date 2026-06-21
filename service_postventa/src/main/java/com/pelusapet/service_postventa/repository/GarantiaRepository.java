package com.pelusapet.service_postventa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelusapet.service_postventa.model.Garantia;

@Repository
public interface GarantiaRepository extends JpaRepository<Garantia, Long>{
  
  List<Garantia> findByUsuarioId(Long usuarioId);

  List<Garantia> findByProductoId(Long productoId);

  List<Garantia> findByOrdenId(Long ordenId);

  List<Garantia> findByEstado(String estado);

}
