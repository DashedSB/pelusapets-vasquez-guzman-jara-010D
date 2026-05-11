package com.pelusapets.service_inventario.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pelusapets.service_inventario.Service.ProductoService;
import com.pelusapets.service_inventario.model.Producto;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET - listar
    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    // GET - buscar por id
    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return productoService.buscar(id);
    }

    // POST - crear
    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    // PUT - actualizar
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    // DELETE - eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}
