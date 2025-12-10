package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Mascota;
import ni.edu.uam.SistemaAprovet.modelo.core.Veterinario;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent; // <--- 1. IMPORTAR ESTO
import java.util.Date;

@Entity
@Getter @Setter
public class Cita {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oidCita;

    @Column(name = "fecha_cita", nullable = false)
    @FutureOrPresent(message = "La fecha de la cita no puede ser en el pasado")
    @Temporal(TemporalType.DATE)
    private Date fechaCita;

    @Column(name = "Motivo_cita", length = 200, nullable = false)
    private String motivo;

    public enum Estado {Pendiente, Realizada, Cancelada}

    @Column(nullable = false)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Mascota mascota;

    @ManyToOne(
            fetch= javax.persistence.FetchType.LAZY,
            optional=false)
    private Veterinario veterinario;
}