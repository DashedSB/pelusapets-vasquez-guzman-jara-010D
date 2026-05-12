package com.pelusapets.service_venta.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orden {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(unique = true)
  private String numeroDeOrden;

  @NotNull(message = "El ID del usuario no puede estar vacio")
  private Long usuarioId;

  @NotNull(message = "El total no puede estar vacio")
  @PositiveOrZero(message = "El total no puede ser negativo")
  private Double total;

  private String estado = "PENDIENTE"; // PENDIENTE, COMPLETADA, CANCELADA

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime fechaVenta;


  @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
  private List<DetalleVenta> items;
  

  

}
