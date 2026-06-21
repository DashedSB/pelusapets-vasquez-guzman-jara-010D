package suscripciones.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import suscripciones.demo.Modelo.Modelo;
import suscripciones.demo.Repository.Repository;
import suscripciones.demo.Service.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ModeloServiceTest {

    @Mock
    private Repository repository;

    @InjectMocks
    private Service service;

    @Test
    void listarDebeRetornarListaDeSuscripciones() {

        // Arrange (Dado)
        Modelo modelo1 = new Modelo();
        Modelo modelo2 = new Modelo();

        List<Modelo> modelos =
                Arrays.asList(modelo1, modelo2);

        when(repository.findAll()).thenReturn(modelos);

        // Act (Cuando)
        List<Modelo> resultado = service.listar();

        // Assert (Entonces)
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void guardarDebeAsignarFechaYEstadoActivaCuandoEstadoEsNull() {

        // Arrange (Dado)
        Modelo modelo = new Modelo();
        modelo.setEstado(null);

        when(repository.save(modelo)).thenReturn(modelo);

        // Act (Cuando)
        Modelo resultado = service.guardar(modelo);

        // Assert (Entonces)
        assertNotNull(resultado.getFechaInicio());
        assertEquals(LocalDate.now(), resultado.getFechaInicio());
        assertEquals("ACTIVA", resultado.getEstado());

        verify(repository).save(modelo);
    }

    @Test
    void guardarDebeMantenerEstadoCuandoYaExiste() {

        // Arrange (Dado)
        Modelo modelo = new Modelo();
        modelo.setEstado("INACTIVA");

        when(repository.save(modelo)).thenReturn(modelo);

        // Act (Cuando)
        Modelo resultado = service.guardar(modelo);

        // Assert (Entonces)
        assertNotNull(resultado.getFechaInicio());
        assertEquals(LocalDate.now(), resultado.getFechaInicio());
        assertEquals("INACTIVA", resultado.getEstado());

        verify(repository).save(modelo);
    }
}