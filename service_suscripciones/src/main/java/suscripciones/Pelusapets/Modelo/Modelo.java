package suscripciones.demo.Modelo;


import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "suscripciones")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;

    private String plan;

    private Double costoMensual;

    private LocalDate fechaInicio;

    private String estado;

    // getters y setters
}