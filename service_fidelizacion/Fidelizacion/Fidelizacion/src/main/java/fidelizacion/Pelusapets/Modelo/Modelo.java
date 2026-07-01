package fidelizacion.Pelusapets.Modelo;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "clientes_fidelizacion")
public class Modelo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;

    private String nombreCliente;

    private Integer puntos;

    private String categoria;

    // getters y setters
}