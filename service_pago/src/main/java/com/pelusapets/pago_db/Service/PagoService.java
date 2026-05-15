package com.pelusapets.pago_db.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelusapets.pago_db.Model.Pago;
import com.pelusapets.pago_db.Repository.PagoRepository;


@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public Pago buscar(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago guardar(Pago pago) {

        // generar código de transacción
        pago.setCodigoTransaccion(UUID.randomUUID().toString());

        // estado automático
        pago.setEstado("PAGADO");

        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
}