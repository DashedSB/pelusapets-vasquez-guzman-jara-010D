package Inventario.pelusapets.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Inventario.pelusapets.Model.Producto;



public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Búsqueda opcional
    Producto findBySku(String sku);

}

