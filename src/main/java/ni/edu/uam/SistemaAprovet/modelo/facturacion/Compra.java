package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Compra {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Proveedor proveedor;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fecha_compra;

    @ElementCollection
    @ListProperties("producto.nombre, producto.precio_venta, cantidad")
    private Collection<Detalle> detalles;

    @Money
    @Column(nullable = false)
    @Required(message = "El precio total es obligatorio")
    private Double total;

    @TextArea
    private String descripcion;


}
