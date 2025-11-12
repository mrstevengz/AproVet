package modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Hidden;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Mascota {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_mascota;

    @Column(name = "nombre_mascota", length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private boolean sexo; // true = macho, false = hembra

    @Column(name = "fecha_nacimiento_mascota", nullable = false)
    private Date fecha_nacimiento;

    @Column(name = "raza_mascota", length = 50, nullable = false)
    private String Raza;

    @ManyToOne( // La referencia se almacena como una relación en la base de datos
            fetch= FetchType.LAZY, // La referencia se carga bajo demanda
            optional=false) // La referencia no puede estar sin valor
    @DescriptionsList // Así la referencia se visualiza usando un combo
    private Cliente cliente;
}
