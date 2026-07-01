package com.pelusapets.service_logistica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pelusapets.service_logistica.model.Proveedor;
import com.pelusapets.service_logistica.services.ProveedorService;
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

@WebMvcTest(ProveedorController.class)
public class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Anotación actualizada
    private ProveedorService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        Proveedor p1 = new Proveedor();
        p1.setNombreEmpresa("Proveedor Test");
        p1.setCorreo("test@test.com");
        
        when(service.listarTodos()).thenReturn(Arrays.asList(p1));

        mockMvc.perform(get("/api/proveedores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreEmpresa").value("Proveedor Test"));
        
        verify(service, times(1)).listarTodos();
    }

    @Test
    void testRegistrar() throws Exception {
        Proveedor nuevo = new Proveedor();
        nuevo.setNombreEmpresa("Distribuidora Canina");
        nuevo.setCorreo("ventas@canina.cl");
        nuevo.setTelefono("+56912345678"); // Campo obligatorio agregado
        nuevo.setCategoria("Alimentos");   // Campo obligatorio agregado

        when(service.guardar(isA(Proveedor.class))).thenReturn(nuevo); // isA previene advertencias de nulos

        mockMvc.perform(post("/api/proveedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreEmpresa").value("Distribuidora Canina"));
        
        verify(service, times(1)).guardar(isA(Proveedor.class));
    }
}