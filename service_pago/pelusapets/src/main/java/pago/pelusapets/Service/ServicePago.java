package pago.pelusapets.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import pago.pelusapets.Repository.ModeloRepository;
import pago.pelusapets.Model.Modelo;

@Service
@SuppressWarnings("null") 
public class ServicePago {

    private final ModeloRepository modeloRepository;

    public ServicePago(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    public List<Modelo> findAll() {
        return modeloRepository.findAll();
    }

    public Modelo buscar(Long id) {
        return modeloRepository.findById(id).orElse(null);
    }

    public Modelo guardar(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    public void eliminar(Long id) {
        modeloRepository.deleteById(id);
    }
}