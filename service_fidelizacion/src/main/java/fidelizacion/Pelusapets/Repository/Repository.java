package fidelizacion.demo.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import fidelizacion.demo.Modelo.Modelo;


public interface Repository
        extends JpaRepository<Modelo, Long> {

    Optional<Modelo> findByRut(String rut);

}