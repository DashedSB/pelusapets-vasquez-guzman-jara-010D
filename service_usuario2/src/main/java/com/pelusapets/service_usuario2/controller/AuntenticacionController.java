package com.pelusapets.service_usuario2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pelusapets.service_usuario2.dto.AuthRequest;
import com.pelusapets.service_usuario2.model.Usuario;
import com.pelusapets.service_usuario2.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Auntenticacion", description = "edpoints para registro y login de usuarios de PelusaPets")
public class AuntenticacionController {

  @Autowired
  private AuthService authService;

  @Operation(summary = "Registrar un nuevo usuario", description = "Guardar el usuario y encripta su contraseña con BCrypt")
  @PostMapping("/registrar")
  public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario){
    return ResponseEntity.ok(authService.registrar(usuario));
  }

  @Operation(summary = "iniciar sesión", description = "Valida las credenciales y retorna un Token JWT válido por 2 horas")
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody AuthRequest request){
    try{
      String token = authService.login(request.getEmail(), request.getPassword());

      return ResponseEntity.ok(token);
    }catch(RuntimeException e){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  


  

}
