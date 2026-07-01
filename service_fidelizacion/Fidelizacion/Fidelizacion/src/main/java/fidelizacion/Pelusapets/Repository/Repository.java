package fidelizacion.Pelusapets.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import fidelizacion.Pelusapets.Modelo.Modelo;


public interface Repository
        extends JpaRepository<Modelo, Long> {

    Optional<Modelo> findByRut(String rut);

}