package com.pelusapets.service_venta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_ventas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "El ID del producto no puede estar vacio")
  private Long productoId;
  private String nombreProducto;

  @NotNull(message = "La cantidad no puede estar vacia")
  @Min(value = 1, message = "La cantidad debe ser al menos 1")
  private Integer cantidad;
  
  @NotNull(message = "El precio unitario es obligatorio")
  @Positive(message = "El precio unitario debe ser mayor a cero")
  private Double precioUnitario;

  @NotNull(message = "El subtotal es obligatorio")
  @Positive(message = "El subtotal debe ser mayor a cero")
  private Double subtotal;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orden_id", nullable = false)
  @JsonIgnore
  private Orden orden;
  

}
