package pago.pelusapets.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pago.pelusapets.Model.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
}