package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Cliente;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Depends;
import org.openxava.annotations.ListProperties;
import org.openxava.annotations.Money;
import org.openxava.annotations.Required;
import org.openxava.annotations.Hidden;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Factura {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(optional = false)
    @Required
    private Cliente cliente;

    @Required
    private LocalDate fechaRegistro = LocalDate.now();

    // ===== COLECCIÓN DE DETALLE =====
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("esServicio, producto.nombre, servicio.nombre, cantidad, precioUnitario, subtotal")
    private Collection<DetalleFactura> detalleFactura;

    // =========================================
    //  MONTO CALCULADO (NO SE GUARDA EN BD)
    // =========================================
    @Money
    @Depends("detalleFactura.subtotal")
    @Transient  // <- para que JPA NO lo mapee a una columna
    public float getMonto() {
        float total = 0;

        if (detalleFactura != null) {
            for (DetalleFactura d : detalleFactura) {
                if (d != null && d.getSubtotal() != 0) {
                    total = total + d.getSubtotal();
                }
            }
        }
        return total;
    }
}