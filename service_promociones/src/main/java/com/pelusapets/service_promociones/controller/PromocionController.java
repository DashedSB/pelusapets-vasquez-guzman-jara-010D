package com.pelusapets.service_promociones.controller;

import com.pelusapets.service_promociones.model.Promocion;
import com.pelusapets.service_promociones.service.PromocionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@CrossOrigin(origins = "*") 
@Tag(name = "Promociones", description = "Gestión de descuentos de la tienda")
@RequiredArgsConstructor
public class PromocionController {

    private final PromocionService service;

    @GetMapping
    @Operation(summary = "Obtener todas las promociones")
    public ResponseEntity<List<Promocion>> listar() {
        return new ResponseEntity<>(service.listarTodas(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear nueva promoción")
    public ResponseEntity<Promocion> crear(@RequestBody Promocion promocion) {
        return new ResponseEntity<>(service.guardar(promocion), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar promoción por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}