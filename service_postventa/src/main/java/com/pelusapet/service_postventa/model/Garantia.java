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
@Table(name = "garantias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Garantia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numero_garantia")
  private String numeroGarantia;

  @Column(name = "orden_id")
  private Long ordenId;

  @Column(name = "producto_id")
  private Long productoId;

  @Column(name = "usuario_id")
  private Long usuarioId;

  @Column(name = "fecha_inicio")
  private LocalDate fechaInicio;

  @Column(name = "fecha_vencimiento")
  private LocalDate fechaVencimiento;

  private String estado = "ACTIVA";
  private String condiciones;
  private boolean vigente = true;

  

}
