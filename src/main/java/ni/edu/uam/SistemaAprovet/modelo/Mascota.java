package ni.edu.uam.SistemaAprovet.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter


public class Mascota {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de la mascota es obligatorio")
    private String nombre;

    public enum Genero {MACHO, HEMBRA}

    @Column(length = 60, nullable = false)
    @Required(message = "El genero de mascota es obligatorio")
    private Genero genero;

    @Required
    private LocalDate fecha_nacimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Simple")
    private Cliente cliente;
}
