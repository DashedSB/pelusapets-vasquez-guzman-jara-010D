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

import com.pelusapet.service_postventa.model.Devoluciones;
import com.pelusapet.service_postventa.service.DevolucionesService;

@RestController
@RequestMapping("/api/devoluciones")
@CrossOrigin(origins = "*")
public class DevolucionesController {

  @Autowired
  private DevolucionesService devolucionesService;
  

  @GetMapping
  public List<Devoluciones> listar(){
    return devolucionesService.listarTodas();
  }

  @GetMapping("/{id}")
  public Devoluciones buscarPorId(@PathVariable Long id){
    return devolucionesService.buscarPorId(id);
  }

  @PostMapping
  public Devoluciones guardar(@RequestBody Devoluciones devolucion){
    return devolucionesService.guardar(devolucion);
  }

  @PutMapping("/{id}")
  public Devoluciones actualizar(@PathVariable Long id, @RequestBody Devoluciones devolucion){
    return devolucionesService.actualiEstado(id, devolucion.getEstado());
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id){
    devolucionesService.eliminar(id);
  }

  @GetMapping("/usuario/{usuarioId}")
  public List<Devoluciones> buscarPorUsuario(@PathVariable Long usuarioId){
    return devolucionesService.buscarPorUsuario(usuarioId);
  }





}
