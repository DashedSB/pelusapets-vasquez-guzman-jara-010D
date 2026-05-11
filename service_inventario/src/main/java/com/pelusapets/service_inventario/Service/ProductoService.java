package com.pelusapets.service_inventario.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapets.service_inventario.model.Producto;
import com.pelusapets.service_inventario.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    // Buscar por ID
    public Producto buscar(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Guardar / Crear
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    // Actualizar
    public Producto actualizar(Long id, Producto producto) {
        Producto existente = buscar(id);
        if (existente != null) {
            existente.setNombreProducto(producto.getNombreProducto());
            existente.setDescripcion(producto.getDescripcion());
            existente.setSku(producto.getSku());
            existente.setStock(producto.getStock());
            existente.setPrecio(producto.getPrecio());
            existente.setCategoria(producto.getCategoria());
            return productoRepository.save(existente);
        }
        return null;
    }
}