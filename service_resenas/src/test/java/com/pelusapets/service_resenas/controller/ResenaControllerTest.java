package com.pelusapets.service_resenas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pelusapets.service_resenas.model.Resena;
import com.pelusapets.service_resenas.service.ResenaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResenaController.class)
class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListar() throws Exception {
        Resena r1 = new Resena();
        r1.setComentario("Buen servicio");
        
        when(service.listarTodas()).thenReturn(Arrays.asList(r1));

        mockMvc.perform(get("/api/resenas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Buen servicio"));
        
        verify(service).listarTodas();
    }

    @Test
    void testCrear() throws Exception {
        Resena nueva = new Resena();
        nueva.setComentario("Excelente");

        when(service.guardar(any(Resena.class))).thenReturn(nueva);

        mockMvc.perform(post("/api/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comentario").value("Excelente"));
        
        verify(service).guardar(any(Resena.class));
    }

    @Test
    void testEliminar() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/resenas/{id}", id))
                .andExpect(status().isNoContent()); 
        
        verify(service).eliminar(id);
    }
}