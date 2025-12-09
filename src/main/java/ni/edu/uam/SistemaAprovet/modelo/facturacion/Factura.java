package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Cliente;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentDateCalculator;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@View(members =
        "Datos Generales {" +
                "fechaRegistro, cliente;" +
                "}" +
                "Detalles {" +
                "detalles;" +
                "}" +
                "Totales {" +
                "monto;" +
                "}"
)
public class Factura {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombreCliente, apellidoCliente")
    @NotNull(message = "Debe seleccionar un cliente")
    private Cliente cliente;

    @Column(nullable = false)
    @Required
    @Stereotype("DATE")
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaRegistro;

    @ElementCollection
    @ListProperties("producto.nombre, servicio.nombre, cantidad, precioUnitario, subtotal")
    private List<DetalleFactura> detalles = new ArrayList<>();

    // =====================
    //  TOTALES
    // =====================

    @ReadOnly
    @Stereotype("MONEY")
    @Column(nullable = false)
    private BigDecimal monto = BigDecimal.ZERO;

    // =====================
    //  L�GICA DE NEGOCIO
    // =====================

    @PrePersist
    @PreUpdate
    private void procesarFactura() {
        recalcularTotal();
        actualizarInventario();
    }

    private void recalcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleFactura d : detalles) {
            total = total.add(d.getSubtotal());
        }
        this.monto = total;
    }

    private void actualizarInventario() {
        for (DetalleFactura d : detalles) {
            // L�gica: Solo descargamos stock si es un PRODUCTO.
            // Los servicios no tienen inventario f�sico.
            if (d.getProducto() != null && d.getProducto().getInventario() != null) {
                // Obtenemos el stock actual (evitando nulos)
                Integer stockActual = d.getProducto().getInventario().getStock();
                if (stockActual == null) stockActual = 0;

                // RESTAMOS el stock (Venta = Salida)
                d.getProducto().getInventario().setStock(stockActual - d.getCantidad());
            }
        }
    }
}
