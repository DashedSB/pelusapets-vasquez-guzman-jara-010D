package suscripciones.Pelusapets.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import suscripciones.Pelusapets.Modelo.Modelo; // Corregido el paquete demo -> Pelusapets
import suscripciones.Pelusapets.Repository.Repository; 

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
        // Arrange
        Modelo modelo1 = new Modelo();
        Modelo modelo2 = new Modelo();
        List<Modelo> modelos = Arrays.asList(modelo1, modelo2);

        when(repository.findAll()).thenReturn(modelos);

        // Act
        List<Modelo> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void guardarDebeAsignarFechaYEstadoActivaCuandoEstadoEsNull() {
        // Arrange
        Modelo modelo = new Modelo();
        modelo.setEstado(null);

        // Usamos returnsFirstArg() para que devuelva el objeto modificado que recibe el repositorio
        when(repository.save(any(Modelo.class))).thenAnswer(returnsFirstArg());

        // Act
        Modelo resultado = service.guardar(modelo);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getFechaInicio());
        assertEquals(LocalDate.now(), resultado.getFechaInicio());
        assertEquals("ACTIVA", resultado.getEstado());

        verify(repository).save(any(Modelo.class));
    }

    @Test
    void guardarDebeMantenerEstadoCuandoYaExiste() {
        // Arrange
        Modelo modelo = new Modelo();
        modelo.setEstado("INACTIVA");

        // Usamos returnsFirstArg() para evitar conflictos de mutabilidad en el mock
        when(repository.save(any(Modelo.class))).thenAnswer(returnsFirstArg());

        // Act
        Modelo resultado = service.guardar(modelo);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getFechaInicio());
        assertEquals(LocalDate.now(), resultado.getFechaInicio());
        assertEquals("INACTIVA", resultado.getEstado());

        verify(repository).save(any(Modelo.class));
    }
}