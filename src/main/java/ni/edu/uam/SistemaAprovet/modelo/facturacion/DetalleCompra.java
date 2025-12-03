package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;

@Entity
@Getter @Setter
public class DetalleCompra {

    @Id
    @Hidden
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    private String oid;

    @ManyToOne(optional = false)
    private Compra compra;

    @ManyToOne(optional = false)
    private Producto producto;

    @ManyToOne
    private Inventario inventario;

    @Required
    private Integer cantidad;

    @Required
    @Stereotype("MONEY")
    @Column(name = "precio_unitario", nullable = false)
    private Float precioUnitario;

    // ==============
    //  SUBTOTAL
    // ==============
    @ReadOnly
    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public Float getSubtotal() {
        if (cantidad == null || precioUnitario == null) return 0f;
        return cantidad * precioUnitario;
    }

    // =========================
    //  CALLBACKS SOLO EN MEMORIA
    // =========================

    @PrePersist
    @PreUpdate
    private void beforeSave() {
        actualizarTotalCompraEnMemoria();
        actualizarStockEnMemoria();
    }

    private void actualizarTotalCompraEnMemoria() {
        if (compra == null || compra.getDetalleCompra() == null) return;

        float total = 0f;
        for (DetalleCompra d : compra.getDetalleCompra()) {
            Float s = d.getSubtotal();
            if (s != null) total += s;
        }
        // además sumamos ESTE detalle por si aún no está en la lista
        Float sEste = getSubtotal();
        if (!compra.getDetalleCompra().contains(this) && sEste != null) {
            total += sEste;
        }
        compra.setTotal(total);
    }

    private void actualizarStockEnMemoria() {
        if (inventario == null || cantidad == null) return;

        Integer stockActual = inventario.getStock() == null ? 0 : inventario.getStock();
        inventario.setStock(stockActual + cantidad);
    }
}
