package com.pelusapets.service_usuario.model;

import lombok.Data;

@Data
public class UsuarioDTO {

  private Long id;
  private String nombreUsuario;
  private String email;
  private String nombreRol;
}
