package com.pelusapets.service_venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pelusapets.service_venta.model.Carrito;
import com.pelusapets.service_venta.services.CarritoService;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {

  @Autowired
  private CarritoService carritoService;

  @GetMapping("/{usuarioId}")
  public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long usuarioId){
    return ResponseEntity.ok(carritoService.obtenerCarrito(usuarioId));
  }

  @PostMapping("/{usuarioId}/items")
  public ResponseEntity<Carrito> agregarItem(@PathVariable Long usuarioId, @RequestParam Long productoId, @RequestParam(defaultValue = "1") Integer cantidad){
    Carrito carritoActualizado = carritoService.agregarItem(usuarioId, productoId, cantidad);
    return ResponseEntity.ok(carritoActualizado);
  }

  @PutMapping("/{usuarioId}/items/{productoId}")
  public ResponseEntity<Carrito> actualizarCantidad(@PathVariable Long usuarioId, @PathVariable Long productoId, @RequestParam Integer cantidad){
    Carrito carritoActualizado = carritoService.actualizarCantidad(usuarioId, productoId, cantidad);
    return ResponseEntity.ok(carritoActualizado);
  }

  @DeleteMapping("/{usuarioId}")
  public ResponseEntity<String> vaciarCarrito(@PathVariable Long usuarioId){
    carritoService.vaciarCarrito(usuarioId);
    return ResponseEntity.ok("Carrito vaciado exitosamente");
  }
}
