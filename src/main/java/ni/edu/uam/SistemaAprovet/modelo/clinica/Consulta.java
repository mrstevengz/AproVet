package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Mascota; // Asegúrate que este import sea correcto
// Importar la clase Cita si está en otro paquete, ej:
// import ni.edu.uam.SistemaAprovet.modelo.medico.Cita;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@View(members =
        "fecha, cita;" +
                "mascota;" +
                "peso;" +
                "sintomas;" +
                "diagnostico"
)
public class Consulta {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Hidden
    private String oid;

    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Short")
    @Required
    private Mascota mascota;

    @OneToOne(fetch = FetchType.LAZY)
    private Cita cita;

    @Stereotype("MEMO")
    private String sintomas;

    @Stereotype("MEMO")
    private String diagnostico;

    @Min(value = 0, message = "El peso no puede ser negativo")
    private double peso;
}