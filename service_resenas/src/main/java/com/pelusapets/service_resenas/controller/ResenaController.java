package com.pelusapets.service_resenas.controller;

import com.pelusapets.service_resenas.model.Resena;
import com.pelusapets.service_resenas.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@CrossOrigin(origins = "*")
@Tag(name = "Reseñas", description = "Gestión de calificaciones y comentarios")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService service;

    @GetMapping
    @Operation(summary = "Listar todas las reseñas")
    public ResponseEntity<List<Resena>> listar() {
        return new ResponseEntity<>(service.listarTodas(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Publicar reseña")
    public ResponseEntity<Resena> crear(@RequestBody Resena resena) {
        return new ResponseEntity<>(service.guardar(resena), HttpStatus.CREATED);
    }

    // Nuevo endpoint agregado para eliminar
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
}