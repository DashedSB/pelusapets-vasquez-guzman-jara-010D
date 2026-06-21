package com.pelusapets.service_promociones.service;

import com.pelusapets.service_promociones.model.Promocion;
import com.pelusapets.service_promociones.repository.PromocionRepository;
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
class PromocionServiceTest {

    @Mock
    private PromocionRepository repository;

    @InjectMocks
    private PromocionService service;

    @Test
    void testListarTodas() {
        Promocion p1 = new Promocion();
        p1.setTitulo("Promo Test");
        
        when(repository.findAll()).thenReturn(Arrays.asList(p1));

        List<Promocion> resultado = service.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals("Promo Test", resultado.get(0).getTitulo());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        Promocion p1 = new Promocion();
        p1.setTitulo("Promo Nueva");
        
        when(repository.save(any(Promocion.class))).thenReturn(p1);

        Promocion resultado = service.guardar(p1);

        assertNotNull(resultado);
        assertEquals("Promo Nueva", resultado.getTitulo());
        verify(repository, times(1)).save(p1);
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        
        service.eliminar(id);
        
        verify(repository, times(1)).deleteById(id);
    }
}