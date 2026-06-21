package com.pelusapets.service_venta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pelusapets.service_venta.model.DetalleVenta;
import com.pelusapets.service_venta.model.Orden;
import com.pelusapets.service_venta.repository.OrdenRepository;
import com.pelusapets.service_venta.services.OrdenService;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {

  @Mock
  private OrdenRepository ordenRepository;

  @InjectMocks
  private OrdenService ordenService;

  @Test
  void crearOrden_CalculaTotalYAsignaValoresPorDefecto(){
    Orden nuevaOrden = new Orden();
    nuevaOrden.setUsuarioId(1L);

    List<DetalleVenta> items = new ArrayList<>();

    DetalleVenta item1 = new DetalleVenta();
    item1.setProductoId(101L);
    item1.setCantidad(2);
    item1.setPrecioUnitario(1500.0);

    DetalleVenta item2 = new DetalleVenta();
    item2.setProductoId(102L);
    item2.setCantidad(1);
    item2.setPrecioUnitario(2000.0);

    items.add(item1);
    items.add(item2);
    nuevaOrden.setItems(items);

    when(ordenRepository.save(any(Orden.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
    Orden resultado = ordenService.creaOrden(nuevaOrden);

    assertEquals(3000.0, resultado.getItems().get(0).getSubtotal(), "El subtotal del item 1 de ser 3000.0");
    assertEquals(2000.0, resultado.getItems().get(1).getSubtotal(), "El subtotal del item 2 debe ser 2000.0");
    assertEquals(5000.0, resultado.getTotal(), "El total de la orden debe ser la suma exacta de los subtotal");

    verify(ordenRepository, times(1)).save(nuevaOrden);
  }

  @Test
  void actualizarEstado_OrdenExistente_ActualizaYGuarda(){
    Long id = 1L;
    String nuevoEstado = "ENVIADO";

    Orden ordenBD = new Orden();
    ordenBD.setId(id);
    ordenBD.setEstado("PAGADO");

    when(ordenRepository.findById(id)).thenReturn(Optional.of(ordenBD));
    when(ordenRepository.save(any(Orden.class))).thenReturn(ordenBD);

    Orden resultado = ordenService.actualizarEstado(id, nuevoEstado);

    assertEquals("ENVIADO", resultado.getEstado(), "El estado debe actualizarse a ENVIADO");
    verify(ordenRepository, times(1)).findById(id);
    verify(ordenRepository, times(1)).save(ordenBD);
  }

  @Test
  void actualizarEstado_OrdenInexistente_LanzaExcepcion(){
    Long idInexistente = 99L;
    when(ordenRepository.findById(idInexistente)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      ordenService.actualizarEstado(idInexistente, "COMPLETADO");
    });

    assertEquals("Orden no encontrada", exception.getMessage());
    verify(ordenRepository, times(1)).findById(idInexistente);
    verify(ordenRepository, never()).save(any(Orden.class));

  }

}
