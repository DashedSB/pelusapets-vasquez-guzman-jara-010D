package com.pelusapets.service_logistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pelusapets.service_logistica.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

   
    Proveedor findByCorreo(String correo); 

    
    @Query("""
        SELECT p FROM Proveedor p
        WHERE p.activo = :activo
        """)
    List<Proveedor> findProveedoresPorEstado(@Param("activo") boolean activo);
}