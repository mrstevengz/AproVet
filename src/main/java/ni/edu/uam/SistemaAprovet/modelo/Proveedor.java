package ni.edu.uam.SistemaAprovet.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
public class Proveedor {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 100, nullable = false)
    @Required(message = "El nombre es un campo obligatorio")
    private String nombre;

    @Column(length = 10, nullable = false)
    @Required(message = "El teléfono es un campo obligatorio")
    private String telefono;

    @Column(length = 100, nullable = false)
    @Required(message = "El email es un campo obligatorio")
    private String email;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaRegistro;

    @Column(nullable = false)
    private boolean estado;
}
