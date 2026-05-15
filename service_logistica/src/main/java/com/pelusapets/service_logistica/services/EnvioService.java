package com.pelusapets.service_logistica.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.pelusapets.service_logistica.model.Envio;
import com.pelusapets.service_logistica.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }

    public Envio guardar(Envio envio) {
        if(envio.getFechaEnvio() == null) {
            envio.setFechaEnvio(LocalDateTime.now());
        }
        return envioRepository.save(envio);
    }

    
    public Envio obtenerEnvioConUsuario(Long id) {
        
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