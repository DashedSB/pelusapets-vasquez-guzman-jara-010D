package fidelizacion.Pelusapets.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fidelizacion.Pelusapets.Modelo.Modelo;
import fidelizacion.Pelusapets.Service.Service;

@RestController
@RequestMapping("/api/modelos")
public class Controller {

    @Autowired
    private Service service;

    @GetMapping
    public List<Modelo> listar() {
        return service.listar();
    }

    @PostMapping
    public Modelo guardar(
            @RequestBody Modelo cliente) {
        return service.guardar(cliente);
    }
}