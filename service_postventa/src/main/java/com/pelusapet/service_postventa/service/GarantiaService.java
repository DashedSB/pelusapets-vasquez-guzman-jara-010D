package com.pelusapet.service_postventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapet.service_postventa.model.Garantia;
import com.pelusapet.service_postventa.repository.GarantiaRepository;

@Service
public class GarantiaService {

  @Autowired
  private GarantiaRepository garantiaRepository;

  public List<Garantia> listarTodas(){
    return garantiaRepository.findAll();
  }

  public Garantia buscarPorId(Long id){
    return garantiaRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Garantía no encontrada con id: " + id));
  }

  public Garantia guardar(Garantia garantia){
    if(garantia.getNumeroGarantia() == null || garantia.getNumeroGarantia().isEmpty()){
      garantia.setNumeroGarantia("GAR-" + System.currentTimeMillis());
    }
    return garantiaRepository.save(garantia);
  }

  public Garantia cancelarGarantia(Long id){
    Garantia garantiaExistentes = buscarPorId(id);
    garantiaExistentes.setEstado("CANCELADA");
    garantiaExistentes.setVigente(false);

    return garantiaRepository.save(garantiaExistentes);
  }

  public List<Garantia> buscarPorUsuario(Long usuarioId){
    return garantiaRepository.findByUsuarioId(usuarioId);
  }

  public List<Garantia> buscarPorProducto(Long productoId){
    return garantiaRepository.findByProductoId(productoId);
  }

  public List<Garantia> buscarPorOrden(Long ordenId){
    return garantiaRepository.findByOrdenId(ordenId);
  }

  public void eliminar(Long id){
    Garantia garantia = buscarPorId(id);
    garantiaRepository.delete(garantia);
  }

}
