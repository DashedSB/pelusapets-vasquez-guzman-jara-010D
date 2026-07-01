package fidelizacion.PelusaPets.TestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fidelizacion.Pelusapets.Modelo.Modelo;
import fidelizacion.Pelusapets.Repository.Repository;
import fidelizacion.Pelusapets.Service.Service;


@ExtendWith(MockitoExtension.class)
class ModeloServiceTest {

    @Mock
    private Repository repository;

    @InjectMocks
    private Service service;

    @Test
    void listarDebeRetornarListaDeModelos() {

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
    void guardarDebeAsignarCategoriaOro() {

        // Arrange (Dado)
        Modelo modelo = new Modelo();
        modelo.setPuntos(1200);

        when(repository.save(modelo)).thenReturn(modelo);

        // Act (Cuando)
        Modelo resultado = service.guardar(modelo);

        // Assert (Entonces)
        assertEquals("ORO", resultado.getCategoria());
        verify(repository).save(modelo);
    }

    @Test
    void guardarDebeAsignarCategoriaPlata() {

        // Arrange (Dado)
        Modelo modelo = new Modelo();
        modelo.setPuntos(700);

        when(repository.save(modelo)).thenReturn(modelo);

        // Act (Cuando)
        Modelo resultado = service.guardar(modelo);

        // Assert (Entonces)
        assertEquals("PLATA", resultado.getCategoria());
        verify(repository).save(modelo);
    }

    @Test
    void guardarDebeAsignarCategoriaBronce() {

        // Arrange (Dado)
        Modelo modelo = new Modelo();
        modelo.setPuntos(300);

        when(repository.save(modelo)).thenReturn(modelo);

        // Act (Cuando)
        Modelo resultado = service.guardar(modelo);

        // Assert (Entonces)
        assertEquals("BRONCE", resultado.getCategoria());
        verify(repository).save(modelo);
    }
}