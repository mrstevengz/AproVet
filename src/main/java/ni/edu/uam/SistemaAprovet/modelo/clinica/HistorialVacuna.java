package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Mascota;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
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
    @Stereotype("DATE")
    private LocalDate fechaAplicacion;

    @Column
    @Stereotype("DATE")
    private LocalDate fechaProxima;

    // 1) fechaAplicacion NO puede ser mayor que hoy
    @AssertTrue(message = "La fecha de aplicación no puede ser mayor que la fecha de hoy")
    private boolean isFechaAplicacionNoFutura() {
        if (fechaAplicacion == null) return true; // mientras el usuario escribe
        LocalDate hoy = LocalDate.now();
        return !fechaAplicacion.isAfter(hoy); // fechaAplicacion <= hoy
    }

    // 2) fechaProxima debe ser MAYOR que hoy
    @AssertTrue(message = "La fecha de la próxima aplicación debe ser posterior a la fecha de hoy")
    private boolean isFechaProximaFutura() {
        if (fechaProxima == null) return true; // si quieres hacerla obligatoria, podemos cambiar esto
        LocalDate hoy = LocalDate.now();
        return fechaProxima.isAfter(hoy); // fechaProxima > hoy
    }

    // 3) fechaAplicacion < fechaProxima
    @AssertTrue(message = "La fecha de aplicación debe ser anterior a la fecha de la próxima aplicación")
    private boolean isConsistenciaEntreFechas() {
        if (fechaAplicacion == null || fechaProxima == null) return true;
        return fechaAplicacion.isBefore(fechaProxima);
    }
}
