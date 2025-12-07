package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Cliente; // <-- ajusta el paquete si es distinto
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter @Setter
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
    private LocalDate fechaRegistro;

    @Money
    @ReadOnly
    private float monto;

    // ===== COLECCIÓN DE DETALLE =====
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("esServicio, producto.nombre, servicio.nombre, cantidad, precioUnitario, subtotal")
    private Collection<FacturaDetalle> detalleFactura;

    // =========================================
    //  RECALCULAR TOTAL ANTES DE GUARDAR
    // =========================================
    @PrePersist
    @PreUpdate
    private void recalcularTotal() {
        float total = 0f;
        if (detalleFactura != null) {
            for (FacturaDetalle d : detalleFactura) {
                if (d != null && d.getCantidad() != null) {
                    total += d.getSubtotal();
                }
            }
        }
        this.monto = total;
    }
}
