package com.pelusapets.pago_db.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pelusapets.pago_db.Model.Pago;



@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

}