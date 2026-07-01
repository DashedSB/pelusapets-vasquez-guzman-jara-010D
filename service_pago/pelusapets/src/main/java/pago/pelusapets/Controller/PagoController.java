package pago.pelusapets.Controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import pago.pelusapets.Model.Modelo;
import pago.pelusapets.Service.ServicePago;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final ServicePago service;

    public PagoController(ServicePago service) {
        this.service = service;
    }

    @GetMapping
    public List<Modelo> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Modelo buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Modelo guardar(@RequestBody Modelo modelo) {
        return service.guardar(modelo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}