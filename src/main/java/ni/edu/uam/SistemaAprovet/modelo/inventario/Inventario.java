package ni.edu.uam.SistemaAprovet.modelo.inventario;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;

@Entity
@Getter @Setter
@View(members =
        "producto;" +
                "Existencias {" +
                "stock, stockMinimo;" +
                "}" +
                "Control de Lotes / Vacunas {" +
                "lote, fechaVencimiento;" +
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

    @Column(nullable = false)
    @Required
    @DefaultValueCalculator(value = org.openxava.calculators.IntegerCalculator.class, properties = @PropertyValue(name="value", value="0"))
    private Integer stock;

    @Column(name = "stock_minimo")
    private Integer stockMinimo = 5;


    @Column(length = 30)
    private String lote;

    @Column(name = "fecha_vencimiento")
    @Stereotype("DATE")
    private LocalDate fechaVencimiento;


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
}
