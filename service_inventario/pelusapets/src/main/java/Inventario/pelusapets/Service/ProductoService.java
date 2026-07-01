package Inventario.pelusapets.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import Inventario.pelusapets.Model.Producto;
import Inventario.pelusapets.Repository.ProductoRepository;

@Service
public class ProductoService {

  
    private final ProductoRepository productoRepository;

    
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

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