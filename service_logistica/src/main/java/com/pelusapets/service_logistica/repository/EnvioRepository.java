package com.pelusapets.service_logistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pelusapets.service_logistica.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    
    List<Envio> findByEstado(String estado);

    
    @Query("""
        SELECT e.estado AS estado,
        COUNT(e) AS cantidad
        FROM Envio e
        GROUP BY e.estado
        """)
    List<Object[]> conteoPorEstado();

    
    @Query("""
        SELECT e FROM Envio e
        JOIN FETCH e.proveedor p
        WHERE p.nombre = :nombreProveedor
        """)
    List<Envio> findEnvioPorNombreProveedor(@Param("nombreProveedor") String nombreProveedor);
}