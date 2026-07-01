package fidelizacion.PelusaPets.TestController; // Ajustado al paquete donde tienes este test según tu explorador de archivos

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Compatible con Spring Boot 3.5+
import org.springframework.test.web.servlet.MockMvc;

import fidelizacion.Pelusapets.Controller.Controller;
import fidelizacion.Pelusapets.Modelo.Modelo;
import fidelizacion.Pelusapets.Service.Service;

@WebMvcTest(controllers = Controller.class)
@AutoConfigureMockMvc(addFilters = false)
class ModeloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Service service;

    @Test
    void listarDeberiaRetornarListaYStatus200() throws Exception {
        // Arrange
        Modelo m1 = new Modelo();
        Modelo m2 = new Modelo();
        List<Modelo> listaMock = Arrays.asList(m1, m2);

        when(service.listar()).thenReturn(listaMock);

        // Act & Assert
        mockMvc.perform(get("/api/modelos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void guardarDeberiaRetornarNuevoModeloConCategoria() throws Exception {
        // Arrange
        Modelo modeloRetorno = new Modelo();
        modeloRetorno.setNombreCliente("Juan");
        modeloRetorno.setPuntos(150);
        modeloRetorno.setCategoria("ORO"); // Atributos reales: nombreCliente, puntos, categoria

        when(service.guardar(any(Modelo.class))).thenReturn(modeloRetorno);

        String jsonCliente = "{\"nombreCliente\": \"Juan\", \"puntos\": 150}";

        // Act & Assert
        mockMvc.perform(post("/api/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCliente))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.nombreCliente").value("Juan"))
                .andExpect(jsonPath("$.puntos").value(150))
                .andExpect(jsonPath("$.categoria").value("ORO"));
    }
}