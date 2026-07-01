package com.pelusapet.service_postventa.service;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pelusapet.service_postventa.model.Reclamacion;
import com.pelusapet.service_postventa.repository.ReclamacionRepository;


@ExtendWith(MockitoExtension.class)
public class ReclamacionServiceTest {

  @Mock
  private ReclamacionRepository reclamacionRepository;

  @InjectMocks
  private ReclamacionService reclamacionService;

  @Test
  void guardar_NuevaReclamacion_AsignaValoresPorDefectoYGuardar(){
    Reclamacion reclamacionVacia = new Reclamacion();
    reclamacionVacia.setAsunto("Saco roto");

    when(reclamacionRepository.save(any(Reclamacion.class))).thenReturn(reclamacionVacia);

    Reclamacion resultado = reclamacionService.guardar(reclamacionVacia);

    assertNotNull(resultado.getNumeroReclamacion(), "Debe generar un número de reclamacion");
    assertTrue(resultado.getNumeroReclamacion().startsWith("REC-"), "El código debe empezar con REC-");
    assertEquals("abierta", resultado.getEstado(), "El estado inicial debe ser abierta");
    assertNotNull(resultado.getFechaReclamacion(), "Debe asignar la fecha de hoy");

    verify(reclamacionRepository, times(1)).save(reclamacionVacia);
  }

  @Test
  void buscarPorId_ReclamacionNoExistente_LanzaExcepcion(){
    Long idInexistente = 99L;
    when(reclamacionRepository.findById(idInexistente)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        reclamacionService.buscarPorId(idInexistente);
    });

    assertEquals("Reclamación no encontrada con id: " + idInexistente, exception.getMessage());
    verify(reclamacionRepository, times(1)).findById(idInexistente);
  }

  @Test
  void responderReclamacion_ActualizarRespuestaEstadoYFecha(){
    Long id = 1L;
    String nuevaRespuesta = "Procesando reembolso";

    Reclamacion reclamacionBD = new Reclamacion();
    reclamacionBD.setId(id);
    reclamacionBD.setEstado("ABIERTA");

    when(reclamacionRepository.findById(id)).thenReturn(Optional.of(reclamacionBD));
    when(reclamacionRepository.save(any(Reclamacion.class))).thenReturn(reclamacionBD);

    Reclamacion resultado = reclamacionService.responderReclamacion(id, nuevaRespuesta);

    assertEquals(nuevaRespuesta, resultado.getRespuesta(), "La respuesta debe actualizarse");
    assertEquals("RESPONDIDA", resultado.getEstado(), "El estado debe cambiar a RESPONDIDOA");
    assertEquals(LocalDate.now(), resultado.getFechaRespuesta(), "Debe asignar la fecha de respuesta de hoy");
    verify(reclamacionRepository, times(1)).save(reclamacionBD);
  }

}
