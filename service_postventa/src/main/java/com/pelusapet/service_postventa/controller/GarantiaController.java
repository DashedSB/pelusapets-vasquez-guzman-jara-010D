package com.pelusapet.service_postventa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pelusapet.service_postventa.model.Garantia;
import com.pelusapet.service_postventa.service.GarantiaService;

@RestController
@RequestMapping("/api/garantia")
@CrossOrigin(origins = "*")
public class GarantiaController {

  @Autowired
  private GarantiaService garantiaService;

  @GetMapping
  public List<Garantia> listar(){
    return garantiaService.listarTodas();
  }

  
  @GetMapping("/{id}")
  public Garantia buscarPorId(@PathVariable Long id){
    return garantiaService.buscarPorId(id);
  }

  @PostMapping
  public Garantia guardar(@RequestBody Garantia garantia){
    return garantiaService.guardar(garantia);
  }

  @GetMapping("/usuario/{usuarioId}")
  public List<Garantia> buscarPorUsuarrio(@PathVariable Long usuarioId){
    return garantiaService.buscarPorUsuario(usuarioId);
  }

  @GetMapping("/producto/{productoId}")
  public List<Garantia> buscarPorProducto(@PathVariable Long productoId){
    return garantiaService.buscarPorProducto(productoId);
  }

  @PutMapping("/{id}/cancelar")
  public Garantia cancelar(@PathVariable Long id){
    return garantiaService.cancelarGarantia(id);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id){
    garantiaService.eliminar(id);
  }




  


  

}
