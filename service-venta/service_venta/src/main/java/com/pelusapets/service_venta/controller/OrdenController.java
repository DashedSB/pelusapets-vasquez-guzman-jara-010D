package com.pelusapets.service_venta.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pelusapets.service_venta.model.Orden;
import com.pelusapets.service_venta.services.OrdenService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/ordenes")
public class OrdenController {

  @Autowired
  private OrdenService ordenService;

  @PostMapping
  public ResponseEntity<Orden> crearOrden(@Valid @RequestBody Orden orden){
    Orden nuevaOrden = ordenService.creaOrden(orden);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
  }
  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerOrden(@PathVariable Long id) {
        
    Optional<Orden> ordenEncontrada = ordenService.obtenerPorId(id);

      if (ordenEncontrada.isPresent()) {
           
          return ResponseEntity.ok(ordenEncontrada.get());
      } else {
           
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("Error: La orden con el ID " + id + " no fue encontrada en el sistema.");
      }
    }

  @GetMapping("/usuario/{usuarioId}")
  public ResponseEntity<Page<Orden>> obtenerOrdenPorUsuario(@PathVariable Long usuarioId, Pageable pageable){
    Page<Orden> ordenes = ordenService.obtenerOrdenesDeUsuarios(usuarioId, pageable);
    return ResponseEntity.ok(ordenes);
  }

  @PutMapping("/{id}/estado")
  public ResponseEntity<Orden> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado){
    try{
      Orden ordenActualizada = ordenService.actualizarEstado(id, nuevoEstado);
      return ResponseEntity.ok(ordenActualizada);
    }catch(RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }
  
  @PutMapping("/{id}/cancelar")
  public ResponseEntity<String> cancelarOrden(@PathVariable Long id){
    try{
      ordenService.cancelarOrden(id);
      return ResponseEntity.ok("Orden cancelada exitosamente");
    }catch(RuntimeException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error:La orden no existe");
    }
  }

}
