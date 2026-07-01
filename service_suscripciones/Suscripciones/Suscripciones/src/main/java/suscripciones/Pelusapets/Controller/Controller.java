package suscripciones.Pelusapets.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import suscripciones.Pelusapets.Modelo.Modelo;
import suscripciones.Pelusapets.Service.Service;



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
            @RequestBody Modelo modelo) {
        return service.guardar(modelo);
    }
}