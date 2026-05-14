package com.pelusapets.service_logistica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Data
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La dirección de destino es obligatoria")
    private String direccionDestino;

    private String estado; // EN_PREPARACION, EN_RUTA, ENTREGADO
    
    private LocalDateTime fechaEnvio;

    // Guarda el ID del cliente que compró
    @Column(nullable = false)
    private Long idUsuario; 

    // Relación: Un envío pertenece a un proveedor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;
}