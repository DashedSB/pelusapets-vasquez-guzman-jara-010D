package com.pelusapets.service_venta.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pelusapets.service_venta.model.DetalleVenta;
import com.pelusapets.service_venta.model.Orden;
import com.pelusapets.service_venta.repository.OrdenRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdenService {

  @Autowired
  private OrdenRepository ordenRepository;

  @Transactional
  public Orden creaOrden(Orden orden) {

    orden.setNumeroDeOrden("ORD-" + System.currentTimeMillis());

    double totalCalculado = 0.0;
    for (DetalleVenta item: orden.getItems()){
      item.setOrden(orden);

      double subtotal = item.getPrecioUnitario() * item.getCantidad();
      item.setSubtotal(subtotal);
      totalCalculado += subtotal;
    }

    orden.setTotal(totalCalculado);
    orden.setEstado("PAGADO");

    return ordenRepository.save(orden);
  }

  public Page<Orden> obtenerOrdenesDeUsuarios(Long usuarioId, Pageable pageable){
    return ordenRepository.findByUsuarioId(usuarioId, pageable);
  }

  public Optional<Orden> obtenerPorId(Long id){
    return ordenRepository.findById(id);
  }

  @Transactional
  public Orden actualizarEstado(Long id, String nuevoEstado){
    Orden orden = ordenRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    orden.setEstado(nuevoEstado);
    return ordenRepository.save(orden);
  }

  @Transactional
  public void cancelarOrden(Long id){
    Orden orden = ordenRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    orden.setEstado("CANCELADO");
    ordenRepository.save(orden);
    
  }

}
