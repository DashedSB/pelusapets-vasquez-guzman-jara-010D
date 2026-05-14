package com.pelusapets.service_logistica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "proveedores")
@Data
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    private String nombreEmpresa;

    @NotBlank(message = "El teléfono de contacto es obligatorio")
    private String telefono;

    @NotBlank(message = "Especifique la categoría (Ej: Alimentos, Accesorios)")
    private String categoria;
    }