package com.pelusapets.service_promociones.service;

import com.pelusapets.service_promociones.model.Promocion;
import com.pelusapets.service_promociones.repository.PromocionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromocionService {

    private final PromocionRepository repository;

    public List<Promocion> listarTodas() {
        return repository.findAll();
    }

    public Promocion guardar(Promocion promocion) {
        return repository.save(promocion);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}