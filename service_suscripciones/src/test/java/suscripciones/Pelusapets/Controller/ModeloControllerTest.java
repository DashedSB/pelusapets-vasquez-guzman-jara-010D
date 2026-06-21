package suscripciones.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import suscripciones.demo.Modelo.Modelo;
import suscripciones.demo.Service.Service;
import suscripciones.demo.Controller.Controller;

@WebMvcTest(Controller.class)
class ModeloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private Service service;

    @Test
    void listarDeberiaRetornarListaDeSuscripcionesYStatus200() throws Exception {
        // Arrange
        Modelo m1 = new Modelo();
        Modelo m2 = new Modelo();
        List<Modelo> listaMock = Arrays.asList(m1, m2);

        // Ahora Mockito sí reconocerá el service correctamente sin dar error
        when(service.listar()).thenReturn(listaMock);

        // Act & Assert
        mockMvc.perform(get("/api/modelos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void guardarDeberiaRetornarNuevaSuscripcionActiva() throws Exception {
        // Arrange
        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        Modelo modeloRetorno = new Modelo();
        modeloRetorno.setFechaInicio(fechaInicio);
        modeloRetorno.setEstado("ACTIVA");

        when(service.guardar(any(Modelo.class))).thenReturn(modeloRetorno);

        String jsonCliente = "{}";

        // Act & Assert
        mockMvc.perform(post("/api/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ACTIVA"))
                .andExpect(jsonPath("$.fechaInicio").value(fechaInicio.toString()));
    }

    @Test
    void guardarDeberiaMantenerEstadoCuandoYaExiste() throws Exception {
        // Arrange
        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        Modelo modeloRetorno = new Modelo();
        modeloRetorno.setFechaInicio(fechaInicio);
        modeloRetorno.setEstado("INACTIVA");

        when(service.guardar(any(Modelo.class))).thenReturn(modeloRetorno);

        String jsonCliente = "{\"estado\": \"INACTIVA\"}";

        // Act & Assert
        mockMvc.perform(post("/api/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("INACTIVA"))
                .andExpect(jsonPath("$.fechaInicio").value(fechaInicio.toString()));
    }
}