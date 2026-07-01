package pago.pelusapets.TestController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; // Usamos MockBean debido a tu versión de Spring Boot
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pago.pelusapets.Controller.PagoController;
import pago.pelusapets.Model.Modelo;
import pago.pelusapets.Service.ServicePago;

@WebMvcTest(controllers = PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
@SuppressWarnings("null")
class TestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicePago service;

    @Test
    void listarDeberiaRetornarListaYStatus200() throws Exception {
        // Arrange
        Modelo m1 = new Modelo();
        Modelo m2 = new Modelo();
        List<Modelo> listaMock = Arrays.asList(m1, m2);

        // CORRECCIÓN AQUÍ: Se simula findAll() que es el método real de tu servicio
        when(service.findAll()).thenReturn(listaMock);

        // Act & Assert
        mockMvc.perform(get("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void guardarDeberiaRetornarNuevoModeloConCategoria() throws Exception {
        // Arrange
        Modelo modeloRetorno = Modelo.builder()
                .idPago(1L)
                .metodoPago("TARJETA_DEBITO")
                .monto(120.50)
                .estado("PENDIENTE")
                .codigoTransaccion("TX-PELUSA11")
                .fechaPago(LocalDateTime.now())
                .build();

        when(service.guardar(any(Modelo.class))).thenReturn(modeloRetorno);

        String jsonPago = "{\"metodoPago\": \"TARJETA_DEBITO\", \"monto\": 120.50, \"estado\": \"PENDIENTE\"}";

        // Act & Assert
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPago))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.metodoPago").value("TARJETA_DEBITO"))
                .andExpect(jsonPath("$.monto").value(120.50))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.codigoTransaccion").value("TX-PELUSA11"));
    }
}