package com.pelusapets.service_resenas.service;

import com.pelusapets.service_resenas.model.Resena;
import com.pelusapets.service_resenas.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository repository;

    public List<Resena> listarTodas() {
        return repository.findAll();
    }

    public Resena guardar(Resena resena) {
        return repository.save(resena);
    }

    // Nuevo método agregado para eliminar
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}