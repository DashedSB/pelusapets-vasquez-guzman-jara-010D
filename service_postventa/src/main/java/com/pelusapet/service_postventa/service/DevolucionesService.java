package com.pelusapet.service_postventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapet.service_postventa.model.Devoluciones;
import com.pelusapet.service_postventa.repository.DevolucionesRepository;

@Service
public class DevolucionesService {

  @Autowired
  private DevolucionesRepository devolucionesRepository;

  public List<Devoluciones> listarTodas(){
    return devolucionesRepository.findAll();
  }

  public Devoluciones buscarPorId(long id){
    return devolucionesRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Devolución no encontrada con id: " + id));
  }

  public Devoluciones guardar(Devoluciones devolucion){
    if (devolucion.getNumeroDevolucion() == null || devolucion.getNumeroDevolucion().isEmpty()){
        devolucion.setNumeroDevolucion("DEV-" + System.currentTimeMillis());
    }
    return devolucionesRepository.save(devolucion);
  }

  public Devoluciones actualiEstado(Long id, String nuevoEstado){
    Devoluciones devolucionesExistentes= buscarPorId(id);
    devolucionesExistentes.setEstado(nuevoEstado.toUpperCase());

    if(nuevoEstado.equalsIgnoreCase("COMPLETADO") || nuevoEstado.equalsIgnoreCase("RECHAZADO")){
      devolucionesExistentes.setFechaDevolucion(java.time.LocalDate.now());
    }
    return devolucionesRepository.save(devolucionesExistentes);
  }

  public void eliminar(long id){
    Devoluciones devolucion= buscarPorId(id);
    devolucionesRepository.delete(devolucion);
  }

  public List<Devoluciones> buscarPorUsuario(Long usuarioId){
    return devolucionesRepository.findByUsuarioId(usuarioId);
  }

}
