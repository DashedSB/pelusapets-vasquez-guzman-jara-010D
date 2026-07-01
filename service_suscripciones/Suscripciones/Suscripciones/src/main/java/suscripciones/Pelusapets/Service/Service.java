package suscripciones.Pelusapets.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import suscripciones.Pelusapets.Modelo.Modelo;
import suscripciones.Pelusapets.Repository.Repository;
@org.springframework.stereotype.Service

public class Service {

    @Autowired
    private Repository repository;

    public List<Modelo> listar() {
        return repository.findAll();
    }

    public Modelo guardar(Modelo modelo) {

        modelo.setFechaInicio(LocalDate.now());

        if (modelo.getEstado() == null) {
            modelo.setEstado("ACTIVA");
        }

        return repository.save(modelo);
    }
}