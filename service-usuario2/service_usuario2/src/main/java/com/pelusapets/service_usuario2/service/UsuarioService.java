package com.pelusapets.service_usuario2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapets.service_usuario2.model.Usuario;
import com.pelusapets.service_usuario2.repository.UsuarioRepository;

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

  public Usuario actualizar(Long id, Usuario usuarioActualizado){
    Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);

    if(usuarioExistente != null){
      usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
      usuarioExistente.setEmail(usuarioActualizado.getEmail());

      if(usuarioActualizado.getRol() != null){
        usuarioExistente.setRol(usuarioActualizado.getRol());
      }
      return usuarioRepository.save(usuarioExistente); 
    }
    return null;
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
