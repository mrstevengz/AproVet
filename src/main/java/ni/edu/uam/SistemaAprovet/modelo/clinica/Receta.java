package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Tab(properties="fecha, indicaciones, numeroMedicamentos")
@View(members =
        "fecha;" +
                "indicaciones;" +
                "detalles"
)
public class Receta {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fecha;
    @TextArea
    private String indicaciones;

    @ElementCollection
    @ListProperties("producto.nombre, dosis, frecuencia, duracion")
    private java.util.List<DetalleReceta> detalles;

    @Transient
    @Depends("detalles") //
    public int getNumeroMedicamentos() {
        return detalles != null ? detalles.size() : 0;
    }
}
