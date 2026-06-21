package com.pelusapet.service_postventa.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devoluciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Devoluciones {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numero_devolucion")
  private String numeroDevolucion;

  @Column(name = "orden_id")
  private Long ordenId;

  @Column(name = "usuario_id")
  private Long usuarioId;

  private String motivo;
  private String descripcion;
  private String estado = "Pendiente"; 

  @Column(name = "monto_reembolso")
  private double montoReembolso;

  @Column(name = "fecha_devolucion")
  private LocalDate fechaDevolucion;


}
