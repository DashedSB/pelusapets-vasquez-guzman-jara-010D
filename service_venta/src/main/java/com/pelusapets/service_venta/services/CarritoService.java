package com.pelusapets.service_venta.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapets.service_venta.model.Carrito;
import com.pelusapets.service_venta.model.CarritoItem;
import com.pelusapets.service_venta.repository.CarritoRepository;

@Service
public class CarritoService {

  @Autowired
  private CarritoRepository carritoRepository;

  public Carrito obtenerCarrito(Long usuarioId){
    return carritoRepository.findByUsuarioId(usuarioId)
      .orElseGet(() -> {
        Carrito nuevCarrito = new Carrito();
        nuevCarrito.setUsuarioId(usuarioId);
        return carritoRepository.save(nuevCarrito);
      });
  }

  public Carrito agregarItem(Long usuarioId, Long productoId, Integer cantidad){
    Carrito carrito = obtenerCarrito(usuarioId);

    Optional<CarritoItem> itemExistente = carrito.getItems().stream()
      .filter(item -> item.getProductoId().equals(productoId))
      .findFirst();

    if(itemExistente.isPresent()){
      CarritoItem item = itemExistente.get();
      item.setCantidad(item.getCantidad() + cantidad);
    }else{
      CarritoItem nuevoItem = new CarritoItem();
      nuevoItem.setProductoId(productoId);
      nuevoItem.setCantidad(cantidad);
      nuevoItem.setCarrito(carrito);
      carrito.getItems().add(nuevoItem);
    }

    return carritoRepository.save(carrito);
  }

  public Carrito actualizarCantidad(Long usuarioId, Long productoId, Integer cantidad){
    Carrito carrito = obtenerCarrito(usuarioId);

    if(cantidad <= 0){
      carrito.getItems().removeIf(item -> item.getProductoId().equals(productoId));
    }else{
      carrito.getItems().stream()
        .filter(item -> item.getProductoId().equals(productoId))
        .findFirst()
        .ifPresent(item -> item.setCantidad(cantidad));
    }
    return carritoRepository.save(carrito);
  }

  public void vaciarCarrito(Long usuarioId){
    Carrito carrito = obtenerCarrito(usuarioId);
    carrito.getItems().clear();
    carritoRepository.save(carrito);
  }
  

}
