package com.pelusapets.service_venta.controller;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pelusapets.service_venta.model.Orden;
import com.pelusapets.service_venta.services.OrdenService;

@ExtendWith(MockitoExtension.class)
public class OrdenControllerTest {

  private MockMvc mockMvc;

  @Mock
  private OrdenService ordenService;

  @InjectMocks
  private OrdenController ordenController;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp(){

    mockMvc = MockMvcBuilders.standaloneSetup(ordenController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void crearOrden_DebeRetornarStatus201YOrdenCreada() throws Exception{
    Orden peticionOrden = new Orden();
    peticionOrden.setUsuarioId(1L);
    peticionOrden.setTotal(0.0);

    Orden ordenCreada = new Orden();
    ordenCreada.setId(10L);
    ordenCreada.setNumeroDeOrden("ORD-9999");
    ordenCreada.setEstado("PAGADO");
    ordenCreada.setUsuarioId(1L);

    when(ordenService.creaOrden(any(Orden.class))).thenReturn(ordenCreada);

    mockMvc.perform(post("/api/v1/ordenes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(peticionOrden)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.numeroDeOrden").value("ORD-9999"))
            .andExpect(jsonPath("$.estado").value("PAGADO"));

    verify(ordenService, times(1)).creaOrden(any(Orden.class));
  }

  @Test
  void obtenerOrden_OrdenExistente_RetornaStatus200() throws Exception{
    Long idBuscando = 5L;
    Orden ordenSimulada = new Orden();
    ordenSimulada.setId(idBuscando);
    ordenSimulada.setNumeroDeOrden("ORD-5555");

    when(ordenService.obtenerPorId(idBuscando)).thenReturn(Optional.of(ordenSimulada));
    
    mockMvc.perform(get("/api/v1/ordenes/{id}", idBuscando)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(idBuscando))
            .andExpect(jsonPath("$.numeroDeOrden").value("ORD-5555"));   
    
    verify(ordenService, times(1)).obtenerPorId(idBuscando);
  }

  @Test
  void obtenerOrden_OrdenNoExistente_RetornarStatus404ConMensaje() throws Exception{
    Long idInexistente = 99L;
    when(ordenService.obtenerPorId(idInexistente)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/ordenes/{id}", idInexistente)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Error: La orden con el ID " + idInexistente + " no fue encontrada en el sistema."));

    verify(ordenService, times(1)).obtenerPorId(idInexistente);
  }
  

}
