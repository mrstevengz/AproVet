package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@View(members =
        "Datos Generales {" +
                "procesada;" +             // Ver el estado (checkbox de solo lectura)
                "fechaCompra, proveedor;" +
                "descripcion;" +
                "}" +
                "Detalles {" +
                "detalleCompra;" +
                "}" +
                "Totales {" +
                "total;" +
                "}"
)
public class Compra {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Proveedor proveedor;

    @Required
    @Stereotype("DATE")
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaCompra;

    @Stereotype("MEMO")
    private String descripcion;

    // ==========================================
    //  CONTROL DE INVENTARIO
    // ==========================================

    @Column(columnDefinition = "boolean default false")
    @ReadOnly
    private boolean procesada = false;

    @ElementCollection
    @ListProperties("producto.nombre, cantidad, precioUnitario, subtotal")
    private List<DetalleCompra> detalleCompra = new ArrayList<>();

    @ReadOnly
    @Stereotype("MONEY")
    private BigDecimal total = BigDecimal.ZERO;

    // =====================
    //  LÓGICA SEGURA
    // =====================

    @PrePersist
    @PreUpdate
    private void ejecutarLogica() {
        recalcularTotal();
        actualizarStockSeguro();
    }

    private void recalcularTotal() {
        BigDecimal t = BigDecimal.ZERO;
        for (DetalleCompra d : detalleCompra) {
            t = t.add(d.getSubtotal());
        }
        this.total = t;
    }

    private void actualizarStockSeguro() {
        if (this.procesada) {
            return;
        }
        boolean huboActualizacion = false;

        for (DetalleCompra d : detalleCompra) {
            if (d.getProducto() != null
                    && d.getProducto().getInventario() != null
                    && d.getCantidad() != null
                    && d.getCantidad() > 0) {

                var inventario = d.getProducto().getInventario();

                int stockActual = (inventario.getStock() == null) ? 0 : inventario.getStock();

                inventario.setStock(stockActual + d.getCantidad());

                huboActualizacion = true;
            }
        }
        if (huboActualizacion) {
            this.procesada = true;
        }
    }
}