package com.pelusapets.service_logistica.services;

import com.pelusapets.service_logistica.model.Proveedor;
import com.pelusapets.service_logistica.repository.ProveedorRepository;
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
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository repository;

    @InjectMocks
    private ProveedorService service;

    @Test
    void testListarTodos() {
        Proveedor p1 = new Proveedor();
        p1.setNombreEmpresa("Proveedor de Prueba");
        p1.setCorreo("contacto@prueba.com");
        p1.setActivo(true);
        
        when(repository.findAll()).thenReturn(Arrays.asList(p1));

        List<Proveedor> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Proveedor de Prueba", resultado.get(0).getNombreEmpresa());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        Proveedor p1 = new Proveedor();
        p1.setNombreEmpresa("Nuevo Proveedor SA");
        p1.setCorreo("nuevo@sa.com");
        
        when(repository.save(any(Proveedor.class))).thenReturn(p1);

        Proveedor resultado = service.guardar(p1);

        assertNotNull(resultado);
        assertEquals("Nuevo Proveedor SA", resultado.getNombreEmpresa());
        verify(repository, times(1)).save(p1);
    }
}