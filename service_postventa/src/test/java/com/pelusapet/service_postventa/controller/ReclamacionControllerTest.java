package com.pelusapet.service_postventa.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pelusapet.service_postventa.model.Reclamacion;
import com.pelusapet.service_postventa.service.ReclamacionService;

@ExtendWith(MockitoExtension.class)
public class ReclamacionControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ReclamacionService reclamacionService;

  @InjectMocks
  private ReclamacionController reclamacionController;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp(){
    mockMvc = MockMvcBuilders.standaloneSetup(reclamacionController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void buscarPorId_DebeRetornarReclamacionYStatus200() throws Exception{
    Long id = 1L;
    Reclamacion reclamacionSimulada = new Reclamacion();
    reclamacionSimulada.setId(id);
    reclamacionSimulada.setAsunto("Problema con el envío");

    when(reclamacionService.buscarPorId(id)).thenReturn(reclamacionSimulada);

    mockMvc.perform(get("/api/reclamaciones/{id}",id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.asunto").value("Problema con el envío"));

    verify(reclamacionService, times(1)).buscarPorId(id);
  }

  @Test
  void guardar_DebeRetornarReclamacionCreadaYStatus200() throws Exception{
    Reclamacion nuevReclamacion = new Reclamacion();
    nuevReclamacion.setAsunto("Producto defectuoso");

    Reclamacion reclamacionGuardada = new Reclamacion();
    reclamacionGuardada.setId(1L);
    reclamacionGuardada.setNumeroReclamacion("REC-12345");
    reclamacionGuardada.setAsunto("Producto defectuoso");
    reclamacionGuardada.setEstado("ABIERTA");

    when(reclamacionService.guardar(any(Reclamacion.class))).thenReturn(reclamacionGuardada);

    mockMvc.perform(post("/api/reclamaciones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevReclamacion)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numeroReclamacion").value("REC-12345"))
            .andExpect(jsonPath("$.estado").value("ABIERTA"));

    verify(reclamacionService, times(1)).guardar(any(Reclamacion.class));
  }
}
