package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Mascota;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class HistorialVacuna {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(optional = false)
    @Required
    private Producto producto;

    @ManyToOne(optional = false)
    @ReferenceView("Short")
    @DescriptionsList(descriptionProperties = "nombreMascota")
    private Mascota mascota;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaAplicacion;

    @Column
    private LocalDate fechaProxima;


}
