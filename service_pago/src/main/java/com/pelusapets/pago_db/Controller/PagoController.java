package com.pelusapets.pago_db.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pelusapets.pago_db.Model.Pago;
import com.pelusapets.pago_db.Service.PagoService;



@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listar() {
        return pagoService.listar();
    }

    @GetMapping("/{id}")
    public Pago buscar(@PathVariable Long id) {
        return pagoService.buscar(id);
    }

    @PostMapping
    public Pago guardar(@RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
    }
}