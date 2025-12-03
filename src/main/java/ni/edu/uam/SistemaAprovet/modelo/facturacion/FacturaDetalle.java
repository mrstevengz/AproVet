package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;


@Entity
@Getter @Setter
public class FacturaDetalle {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(optional = false)
    private Factura factura;

    @ManyToOne
    private Producto producto;   // solo se llena si es producto

    @ManyToOne
    private Servicio servicio;   // solo se llena si es servicio

    @Column(nullable = false)
    @Required
    private Integer cantidad;

    @Money
    @Column(nullable = false)
    @Required
    private float precioUnitario;

    @Money
    @ReadOnly
    @Depends("cantidad, precioUnitario")
    public float getSubtotal() {
        if (cantidad == null || precioUnitario == 0) return 0;
        return precioUnitario * cantidad;
    }

    // ===== INVENTARIO: solo si hay PRODUCTO (no servicio) =====

    @PrePersist
    private void disminuirStockAlVender() {
        if (producto == null || cantidad == null) return;   // es servicio -> no toca inventario

        Inventario inv = producto.getInventario();
        if (inv == null) return;  // por si acaso

        inv.setStock(inv.getStock() - cantidad);  // VENTA => baja stock
    }

    @PreRemove
    private void devolverStockSiBorro() {
        if (producto == null || cantidad == null) return;

        Inventario inv = producto.getInventario();
        if (inv == null) return;

        inv.setStock(inv.getStock() + cantidad);  // deshago la venta
    }
}
