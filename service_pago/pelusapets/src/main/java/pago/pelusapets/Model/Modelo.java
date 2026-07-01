package pago.pelusapets.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private String metodoPago;
    private Double monto;
    private String estado;
    private String codigoTransaccion;
    private LocalDateTime fechaPago;
}