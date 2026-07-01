package com.pelusapets.service_logistica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pelusapets.service_logistica.model.Envio;
import com.pelusapets.service_logistica.services.EnvioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Importación moderna
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.isA; // Usamos isA en lugar de any
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Anotación actualizada
    private EnvioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        Envio e1 = new Envio();
        e1.setDireccionDestino("Calle Falsa 123");
        
        when(service.listarTodos()).thenReturn(Arrays.asList(e1));

        mockMvc.perform(get("/api/envios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].direccionDestino").value("Calle Falsa 123"));
        
        verify(service, times(1)).listarTodos();
    }

    @Test
    void testRegistrar() throws Exception {
        Envio nuevo = new Envio();
        nuevo.setDireccionDestino("Avenida Los Gatos 456");

        when(service.guardar(isA(Envio.class))).thenReturn(nuevo);

        mockMvc.perform(post("/api/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.direccionDestino").value("Avenida Los Gatos 456"));
        
        verify(service, times(1)).guardar(isA(Envio.class));
    }
}