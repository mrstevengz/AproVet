package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.core.Veterinario;
import ni.edu.uam.SistemaAprovet.modelo.facturacion.Servicio;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

import javax.persistence.*;
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
    private Date fechaCita;

    @Column(name = "Motivo_cita", length = 200, nullable = false)
    private String motivo;

    public enum Estado {Pendiente, Realizada, Cancelada}

    @Column(nullable = false)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Mascota mascota;

    @ManyToOne( // La referencia se almacena como una relación en la base de datos
            fetch= javax.persistence.FetchType.LAZY, // La referencia se carga bajo demanda
            optional=false) // La referencia no puede estar sin valor
    private Veterinario veterinario;
}
