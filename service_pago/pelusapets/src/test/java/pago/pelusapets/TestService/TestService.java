package pago.pelusapets.TestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pago.pelusapets.Model.Modelo;
import pago.pelusapets.Repository.ModeloRepository;
import pago.pelusapets.Service.ServicePago;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class TestService {

    @Mock
    private ModeloRepository modeloRepository;


    private ServicePago servicePago;

    private Modelo modeloPrueba;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
      
        servicePago = new ServicePago(modeloRepository);

        modeloPrueba = Modelo.builder()
                .idPago(1L)
                .metodoPago("TRANSF_BANCARIA")
                .monto(350.00)
                .estado("COMPLETADO")
                .codigoTransaccion("TX-PELUSA99")
                .fechaPago(LocalDateTime.now())
                .build();
    }

    @Test
    void findAll_DebeRetornarListaDeModelos() {
        when(modeloRepository.findAll()).thenReturn(Arrays.asList(modeloPrueba));

        List<Modelo> resultado = servicePago.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("TRANSF_BANCARIA", resultado.get(0).getMetodoPago());
        verify(modeloRepository, times(1)).findAll();
    }

    @Test
    void buscar_DebeRetornarModelo_CuandoExiste() {
        when(modeloRepository.findById(1L)).thenReturn(Optional.of(modeloPrueba));

        Modelo resultado = servicePago.buscar(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPago());
        assertEquals("COMPLETADO", resultado.getEstado());
    }

    @Test
    void guardar_DebeRetornarModeloGuardado() {
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modeloPrueba);

        Modelo resultado = servicePago.guardar(modeloPrueba);

        assertNotNull(resultado);
        assertEquals("TX-PELUSA99", resultado.getCodigoTransaccion());
        verify(modeloRepository, times(1)).save(any(Modelo.class));
    }

    @Test
    void eliminar_DebeLlamarAlMetodoDelete() {
        doNothing().when(modeloRepository).deleteById(1L);

        servicePago.eliminar(1L);

        verify(modeloRepository, times(1)).deleteById(1L);
    }
}