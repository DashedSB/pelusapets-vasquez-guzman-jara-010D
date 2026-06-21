package com.pelusapets.service_usuario2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pelusapets.service_usuario2.model.Usuario;
import com.pelusapets.service_usuario2.repository.UsuarioRepository;

@Service
public class AuthService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Usuario registrar(Usuario usuario){
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    return usuarioRepository.save(usuario);
  }

  public String login(String email, String password){
    Usuario user = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    if(passwordEncoder.matches(password, user.getPassword())){
      String nombreRol = user.getRol().getNombreRol();
      return jwtService.generarToken(email, List.of(nombreRol));
    }

    throw new RuntimeException("Credenciales inválidas, contraseña incorrecta.");
  }


}
