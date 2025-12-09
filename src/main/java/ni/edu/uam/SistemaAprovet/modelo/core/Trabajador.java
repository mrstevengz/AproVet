package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Trabajador {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 100, nullable = false)
    @Required(message="El nombre es un campo obligatorio")
    private String nombre;

    @Column(length = 40)
    private String cargo;

    @Column(length = 18, nullable = false, unique = true)
    private String cedula;

    @Column(length = 10, nullable = false)
    @Stereotype("MONEY")
    private BigDecimal salario;

    @Column(length = 12, nullable = false)
    private String telefono;

    @Required
    private LocalDate fechaContratacion;

    @Column(length = 100, nullable = false)
    @Required(message="La contraseña es un campo obligatorio")
    @Stereotype("PASSWORD")
    private String contrasenna;
}
