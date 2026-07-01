package com.pelusapets.service_usuario2.dto;

import lombok.Data;

@Data
public class AuthRequest {

  private String email;
  private String password;

}
