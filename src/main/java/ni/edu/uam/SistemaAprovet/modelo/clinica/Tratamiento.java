package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.facturacion.Servicio;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Hidden;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Tratamiento {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_tratamiento;

    @ManyToOne( // La referencia se almacena como una relación en la base de datos
            fetch= FetchType.LAZY, // La referencia se carga bajo demanda
            optional=false)
    @DescriptionsList
    private Servicio servicio;

    @ManyToOne( // La referencia se almacena como una relación en la base de datos
            fetch= FetchType.LAZY, // La referencia se carga bajo demanda
            optional=false) // La referencia no puede estar sin valor
    @DescriptionsList
    private Historial historial;

    @ManyToOne( // La referencia se almacena como una relación en la base de datos
            fetch= FetchType.LAZY, // La referencia se carga bajo demanda
            optional=true) // La referencia no puede estar sin valor
    @DescriptionsList
    private Cita cita;

    @OneToOne(fetch= FetchType.LAZY, // La referencia se carga bajo demanda
            optional=false)
    @DescriptionsList
    private Producto producto;

    @Column(name = "dosis_mililitros", nullable = false)
    @Getter @Setter
    private float dosis_mililitros;

    @Column(name= "fecha_inicio_tratamiento", nullable = false)
    private Date fecha_inicio;

    @Column(name= "fecha_final_tratamiento", nullable = true)
    private Date fecha_final;

    @Column(name = "observaciones_tratamiento", length = 200, nullable = true)
    private String observaciones;
}
