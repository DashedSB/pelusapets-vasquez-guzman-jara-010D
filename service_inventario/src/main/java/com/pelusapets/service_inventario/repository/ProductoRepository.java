package com.pelusapets.service_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelusapets.service_inventario.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Búsqueda opcional
    Producto findBySku(String sku);

}

