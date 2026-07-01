package com.pelusapets.service_logistica.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.lang.NonNull; // Importación oficial para nulidad
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.pelusapets.service_logistica.model.Envio;
import com.pelusapets.service_logistica.repository.EnvioRepository;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    private final WebClient.Builder webClientBuilder;

    EnvioService(EnvioRepository envioRepository, WebClient.Builder webClientBuilder) {
        this.envioRepository = envioRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }

    // Le decimos al IDE que este envio jamás será nulo
    public Envio guardar(@NonNull Envio envio) {
        if(envio.getFechaEnvio() == null) {
            envio.setFechaEnvio(LocalDateTime.now());
        }
        return envioRepository.save(envio);
    }

    // Le decimos al IDE que este ID jamás será nulo
    public Envio obtenerEnvioConUsuario(@NonNull Long id) {
        
        Envio envio = envioRepository.findById(id).orElse(null);
        
        if (envio != null && envio.getIdUsuario() != null) {
            try {
                Object usuario = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/api/usuarios/" + envio.getIdUsuario())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block(); 
                
                envio.setDatosUsuario(usuario);
                
            } catch (Exception e) {
                envio.setDatosUsuario("Información del usuario no disponible (Servicio caído)");
            }
        }
        return envio;
    }
}