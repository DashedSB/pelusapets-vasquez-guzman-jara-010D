package com.pelusapets.service_venta.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "carrito_id", nullable = false)
  @JsonIgnore
  private Carrito carrito;

  @NotNull(message = "El ID del producto es obligatorio")
  @Column(nullable = false)
  private Long productoId;

  @NotNull(message = "debe de especificar la cantidad")
  @Min(value = 1, message = "La cantidad debe ser al menos 1")
  @Column(nullable = false)
  private Integer cantidad;
  


}
