package com.pelusapets.service_logistica.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pelusapets.service_logistica.model.Proveedor;
import com.pelusapets.service_logistica.services.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarTodos() {
        return new ResponseEntity<>(proveedorService.listarTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Proveedor> registrar(@Valid @RequestBody Proveedor proveedor) {
        return new ResponseEntity<>(proveedorService.guardar(proveedor), HttpStatus.CREATED);
    }
}