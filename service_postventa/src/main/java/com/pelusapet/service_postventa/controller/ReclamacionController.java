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

import com.pelusapet.service_postventa.model.Reclamacion;
import com.pelusapet.service_postventa.service.ReclamacionService;

@RestController
@RequestMapping("/api/reclamaciones")
@CrossOrigin(origins = "*")
public class ReclamacionController {

  @Autowired
  private ReclamacionService reclamacionService;

  @GetMapping
  public List<Reclamacion> listar(){
    return reclamacionService.listarTodas();
  }

  @GetMapping("/{id}")
  public Reclamacion buscarPorId(@PathVariable Long id){
    return reclamacionService.buscarPorId(id);
  }

  @PostMapping
  public Reclamacion guardar(@RequestBody Reclamacion reclamacion){
    return reclamacionService.guardar(reclamacion);
  }

  @PutMapping("/{id}/responder")
  public Reclamacion responder(@PathVariable Long id, @RequestBody Reclamacion reclamacion){
    return reclamacionService.responderReclamacion(id, reclamacion.getRespuesta());
  }

  @PutMapping("/{id}/cerrar")
  public Reclamacion cerrar(@PathVariable Long id){
    return reclamacionService.cerrarReclamacion(id);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id){
    reclamacionService.eliminar(id);
  }

  @GetMapping("/usuario/{usuarioId}")
  public List<Reclamacion> buscarPorUsuario(@PathVariable Long usuarioId){
    return reclamacionService.buscarPorUsuario(usuarioId);
  }



}
