package ni.edu.uam.SistemaAprovet.modelo.inventario;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Getter @Setter
@View(members =
        "producto;" +
                "Existencias {" +
                "   stock, stockMinimo;" +
                "}" +
                "Control de Lotes / Vacunas {" +
                "   lote, fechaVencimiento;" +
                "}"
)
public class Inventario {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Simple")
    private Producto producto;

    // STOCK: no nulo y no negativo
    @Column(nullable = false)
    @Required
    @Min(value = 0, message = "El stock no puede ser negativo")
    @DefaultValueCalculator(
            value = org.openxava.calculators.IntegerCalculator.class,
            properties = @PropertyValue(name = "value", value = "0")
    )
    private Integer stock;

    // STOCK MÍNIMO: no negativo
    @Column(name = "stock_minimo", nullable = false)
    @Required
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo = 5;

    @Column(length = 30)
    private String lote;

    @Column(name = "fecha_vencimiento")
    @Stereotype("DATE")
    private LocalDate fechaVencimiento;

    // ===== VALIDACIÓN PARA VACUNAS =====
    @AssertTrue(message = "Las vacunas requieren Lote y Fecha de Vencimiento obligatorios")
    private boolean isDatosVacunaCompletos() {
        if (producto != null && producto.getCategoria() != null) {
            String nombreCat = producto.getCategoria().getNombre().toUpperCase();

            if (nombreCat.contains("VACUNA")) {
                return lote != null && !lote.isEmpty() && fechaVencimiento != null;
            }
        }
        return true;
    }

    // ===== VALIDACIÓN RELACIÓN STOCK vs STOCK MÍNIMO =====
    @AssertTrue(message = "El stock no puede ser menor que el stock mínimo")
    private boolean isStockMayorIgualStockMinimo() {
        // Mientras el usuario escribe, evita romper si algo está vacío
        if (stock == null || stockMinimo == null) return true;

        return stock >= stockMinimo;
    }
}
