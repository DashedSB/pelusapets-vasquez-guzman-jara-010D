package com.pelusapets.service_logistica.services;

import java.util.List;

import org.springframework.lang.NonNull; // Importación oficial para nulidad
import org.springframework.stereotype.Service;
import com.pelusapets.service_logistica.model.Proveedor;
import com.pelusapets.service_logistica.repository.ProveedorRepository;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    // Le decimos al IDE que este proveedor jamás será nulo
    public Proveedor guardar(@NonNull Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
}