package com.pelusapets.service_logistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.pelusapets.service_logistica.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Ahora sí funcionará porque agregamos 'correo' al modelo
    Proveedor findByCorreo(String correo); 

    // Ahora sí funcionará porque agregamos 'activo' al modelo
    @Query("""
        SELECT p FROM Proveedor p
        WHERE p.activo = :activo
        """)
    List<Proveedor> findProveedoresPorEstado(@Param("activo") boolean activo);
}