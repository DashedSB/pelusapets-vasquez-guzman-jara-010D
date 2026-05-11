package com.pelusapets.service_usuario.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapets.service_usuario.model.Usuario;
import com.pelusapets.service_usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  public List<Usuario> listarTodos(){
    return usuarioRepository.findAll();
  }

  public Optional<Usuario> buscarPorId(Long id){
    return usuarioRepository.findById(id);
  }

  @Transactional
  public Usuario guardar(Usuario usuario){
    if(usuario.getEmail() != null){
      usuario.setEmail(usuario.getEmail().toLowerCase());
    }
    return usuarioRepository.save(usuario);
  }

  public void eliminar(Long id){
    usuarioRepository.deleteById(id);
  }

}
