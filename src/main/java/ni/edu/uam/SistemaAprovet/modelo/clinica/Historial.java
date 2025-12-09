package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Mascota;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.Hidden;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Historial {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Mascota mascota;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fecha_creacion = LocalDate.now();
}
