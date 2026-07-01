package Inventario.pelusapets.TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Inventario.pelusapets.Model.Producto;
import Inventario.pelusapets.Repository.ProductoRepository;
import Inventario.pelusapets.Service.ProductoService;

@ExtendWith(MockitoExtension.class)
public class TestService {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombreProducto("Alimento Pelusa Pets");
        producto.setPrecio(15000.0);
        producto.setStock(10);
        producto.setSku("PROD123");
    }

    @Test
    void testListar() {
        // Configurar comportamiento del mock
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));

        // Ejecutar método del servicio
        List<Producto> resultado = productoService.listar();

        // Verificar resultados
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Alimento Pelusa Pets", resultado.get(0).getNombreProducto());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Existente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscar(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_NoExistente() {
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());

        Producto resultado = productoService.buscar(2L);

        assertNull(resultado);
        verify(productoRepository, times(1)).findById(2L);
    }

    @Test
    void testGuardar() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.guardar(producto);

        // Corregido: Se elimina el .get(0) y el comentario sobrante
        assertNotNull(resultado);
        assertEquals("Alimento Pelusa Pets", resultado.getNombreProducto()); 
        assertEquals("PROD123", resultado.getSku());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testEliminar() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminar(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}