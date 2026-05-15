package com.pelusapets.service_logistica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Data
public class Envio {
    @Transient
    private Object datosUsuario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La dirección de destino es obligatoria")
    private String direccionDestino;

    private String estado; 
    
    private LocalDateTime fechaEnvio;

    
    @Column(nullable = false)
    private Long idUsuario; 

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;
}