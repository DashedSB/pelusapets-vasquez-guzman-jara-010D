package com.pelusapet.service_postventa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapet.service_postventa.model.Reclamacion;
import com.pelusapet.service_postventa.repository.ReclamacionRepository;

@Service
public class ReclamacionService {

  @Autowired
  private ReclamacionRepository reclamacionRepository;

  public List<Reclamacion> listarTodas(){
    return reclamacionRepository.findAll();
  }

  public Reclamacion buscarPorId(long id){
    return reclamacionRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Reclamación no encontrada con id: " + id));
  }

  public Reclamacion guardar(Reclamacion reclamacion){
    if(reclamacion.getNumeroReclamacion() == null || reclamacion.getNumeroReclamacion().isEmpty()){
        reclamacion.setNumeroReclamacion("REC-" + System.currentTimeMillis());
    }

    if(reclamacion.getFechaReclamacion() == null){
      reclamacion.setFechaReclamacion(java.time.LocalDate.now());
    }

    if(reclamacion.getEstado() == null){
      reclamacion.setEstado("ABIERTA");
    }
    return reclamacionRepository.save(reclamacion);
  }

  public Reclamacion responderReclamacion(Long id, String reespuesta){
    Reclamacion reclamacionExistente = buscarPorId(id);

    reclamacionExistente.setRespuesta(reespuesta);
    reclamacionExistente.setEstado("RESPONDIDA");
    reclamacionExistente.setFechaRespuesta(LocalDate.now());

    return reclamacionRepository.save(reclamacionExistente);
  }

  public Reclamacion cerrarReclamacion(Long id){
    Reclamacion reclamacionExistente = buscarPorId(id);
    reclamacionExistente.setEstado("CERRADO");

    return reclamacionRepository.save(reclamacionExistente);
  }

  public List<Reclamacion> buscarPorUsuario(Long usuarioId){
    return reclamacionRepository.findByUsuarioId(usuarioId);
  }

  public void eliminar(long id){
    Reclamacion reclamacion = buscarPorId(id);
    reclamacionRepository.delete(reclamacion);
  }
}
