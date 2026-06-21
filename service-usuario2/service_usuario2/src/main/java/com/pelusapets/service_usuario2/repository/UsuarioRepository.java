package com.pelusapets.service_usuario2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pelusapets.service_usuario2.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

 Optional <Usuario> findByEmail(String email);

  @Query("""
      SELECT u.rol.nombreRol AS rol,
      COUNT(u) AS cantidad
      FROM Usuario u
      GROUP BY u.rol.nombreRol
      """)
  List<Object[]> conteoPorRol();

  @Query("""
      SELECT u FROM Usuario u
      JOIN FETCH u.rol r
      WHERE r.nombreRol = :nombreRol
      """)
  List<Usuario> findUsuarioPorNombreRol(@Param("nombreRol") String nombreRol);

}
