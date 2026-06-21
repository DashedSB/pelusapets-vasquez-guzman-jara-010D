package com.pelusapets.service_promociones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pelusapets.service_promociones.model.Promocion;
import com.pelusapets.service_promociones.service.PromocionService;
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

@WebMvcTest(PromocionController.class)
class PromocionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PromocionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListar() throws Exception {
        Promocion p1 = new Promocion();
        p1.setTitulo("Promo Verano");
        
        when(service.listarTodas()).thenReturn(Arrays.asList(p1));

        mockMvc.perform(get("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Promo Verano"));
        
        verify(service).listarTodas();
    }

    @Test
    void testCrear() throws Exception {
        Promocion nueva = new Promocion();
        nueva.setTitulo("Flash Sale");

        when(service.guardar(any(Promocion.class))).thenReturn(nueva);

        mockMvc.perform(post("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Flash Sale"));
        
        verify(service).guardar(any(Promocion.class));
    }

    @Test
    void testEliminar() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/promociones/{id}", id))
                .andExpect(status().isNoContent()); 
        
        verify(service).eliminar(id);
    }
}