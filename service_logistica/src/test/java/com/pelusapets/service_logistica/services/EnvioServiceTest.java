package com.pelusapets.service_logistica.services;

import com.pelusapets.service_logistica.model.Envio;
import com.pelusapets.service_logistica.repository.EnvioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository repository;

    @InjectMocks
    private EnvioService service;

    @Test
    void testListarTodos() {
        Envio e1 = new Envio();
        e1.setDireccionDestino("Calle Test 123");
        
        when(repository.findAll()).thenReturn(Arrays.asList(e1));

        List<Envio> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Calle Test 123", resultado.get(0).getDireccionDestino());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        Envio e1 = new Envio();
        e1.setDireccionDestino("Direccion Nueva 456");
        
        when(repository.save(any(Envio.class))).thenReturn(e1);

        Envio resultado = service.guardar(e1);

        assertNotNull(resultado);
        assertEquals("Direccion Nueva 456", resultado.getDireccionDestino());
        verify(repository, times(1)).save(e1);
    }
}