package com.pelusapets.service_logistica.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pelusapets.service_logistica.model.Envio;
import com.pelusapets.service_logistica.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }

    public Envio guardar(Envio envio) {
        // Genera la fecha actual automáticamente al registrar el envío
        if(envio.getFechaEnvio() == null) {
            envio.setFechaEnvio(LocalDateTime.now());
        }
        return envioRepository.save(envio);
    }
}