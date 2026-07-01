package fidelizacion.Pelusapets.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fidelizacion.Pelusapets.Modelo.Modelo;
import fidelizacion.Pelusapets.Repository.Repository;


@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repository repository;

    public List<Modelo> listar() {
        return repository.findAll();
    }

    public Modelo guardar(Modelo modelo) {

        if(modelo.getPuntos() >= 1000) {
            modelo.setCategoria("ORO");
        } else if(modelo.getPuntos() >= 500) {
            modelo.setCategoria("PLATA");
        } else {
            modelo.setCategoria("BRONCE");
        }

        return repository.save(modelo);
    }
}