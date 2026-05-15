package com.pelusapets.pago_db.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private String metodoPago;

    private Double monto;

    private String estado;

    private String codigoTransaccion;

    private LocalDateTime fechaPago;

    public Pago() {
        this.fechaPago = LocalDateTime.now();
    }

  
}