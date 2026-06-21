package com.pelusapets.service_resenas.service;

import com.pelusapets.service_resenas.model.Resena;
import com.pelusapets.service_resenas.repository.ResenaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock
    private ResenaRepository repository;

    @InjectMocks
    private ResenaService service;

    @Test
    void testListarTodas() {
        // --- Arrange (Dado) ---
        Resena r1 = new Resena();
        r1.setComentario("Buen servicio");
        Resena r2 = new Resena();
        r2.setComentario("Excelente atención");
        
        when(repository.findAll()).thenReturn(Arrays.asList(r1, r2));

        // --- Act (Cuando) ---
        List<Resena> resultado = service.listarTodas();

        // --- Assert (Entonces) ---
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void testGuardar() {
        // --- Arrange (Dado) ---
        Resena nueva = new Resena();
        nueva.setComentario("Muy rápido");
        
        when(repository.save(any(Resena.class))).thenReturn(nueva);

        // --- Act (Cuando) ---
        Resena resultado = service.guardar(nueva);

        // --- Assert (Entonces) ---
        assertNotNull(resultado);
        assertEquals("Muy rápido", resultado.getComentario());
        verify(repository).save(nueva);
    }
}