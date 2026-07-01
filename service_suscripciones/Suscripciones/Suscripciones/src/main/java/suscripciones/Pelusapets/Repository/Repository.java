package suscripciones.Pelusapets.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import suscripciones.Pelusapets.Modelo.Modelo;

public interface Repository
        extends JpaRepository<Modelo, Long> {

    List<Modelo> findByEstado(String estado);

    List<Modelo> findByPlan(String plan);

}