package com.pelusapets.service_logistica.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pelusapets.service_logistica.model.Envio;
import com.pelusapets.service_logistica.services.EnvioService;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listarTodos() {
        return new ResponseEntity<>(envioService.listarTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Envio> registrar(@Valid @RequestBody Envio envio) {
        return new ResponseEntity<>(envioService.guardar(envio), HttpStatus.CREATED);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarPorId(@PathVariable Long id) {
        // Usamos el nuevo método que creamos en el Service
        Envio envio = envioService.obtenerEnvioConUsuario(id);
        
        if (envio != null) {
            return new ResponseEntity<>(envio, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}