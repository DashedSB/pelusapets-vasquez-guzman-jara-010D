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
@Table(name = "reclamaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reclamacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numero_reclamacion")
  private String numeroReclamacion;

  @Column(name = "orden_id")
  private Long ordenId;

  @Column(name = "usuario_id")
  private Long usuarioId;

  private String tipo;
  private String asunto;
  private String descripcion;
  private String prioridad = "Media";
  private String estado = "abierta";
  private String respuesta; 

  @Column(name = "fecha_reclamacion")
  private LocalDate fechaReclamacion;

  @Column(name = "fecha_respuesta")
  private LocalDate fechaRespuesta;

}
