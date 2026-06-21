package fidelizacion.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// using fully-qualified annotation below to avoid import resolution issues
import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import fidelizacion.demo.Controller.Controller;
import fidelizacion.demo.Modelo.Modelo;
import fidelizacion.demo.Service.Service;

@WebMvcTest(controllers = Controller.class)
@AutoConfigureMockMvc(addFilters = false)
class ModeloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @org.springframework.boot.test.mock.mockito.MockBean
    private Service service; // Simulamos el servicio que ya probaste antes

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
                .andExpect(status().isOk()) // Verifica HTTP 200
                .andExpect(jsonPath("$", Matchers.hasSize(2))); // Verifica que vengan 2 elementos
    }

    @Test
    void guardarDeberiaRetornarModeloConCategoriaOro() throws Exception {
        // Arrange
        Modelo modeloRetorno = new Modelo();
        modeloRetorno.setPuntos(1200);
        modeloRetorno.setCategoria("ORO"); // El servicio ya le asignó ORO

        when(service.guardar(any(Modelo.class))).thenReturn(modeloRetorno);

        // JSON que simula lo que envía el cliente (un modelo con 1200 puntos)
        String jsonCliente = "{\"puntos\": 1200}";

        // Act & Assert
        mockMvc.perform(post("/api/modelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCliente))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.categoria").value("ORO")) // Verifica que el JSON de respuesta diga "ORO"
                .andExpect(jsonPath("$.puntos").value(1200));
    }
}