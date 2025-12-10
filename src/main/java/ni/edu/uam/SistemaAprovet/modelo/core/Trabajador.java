package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;        // <--- Importante
import javax.validation.constraints.PositiveOrZero; // <--- Importante
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
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    private String nombre;

    @Column(length = 40)
    private String cargo;

    @Column(length = 18, nullable = false, unique = true)
    private String cedula;

    @Column(length = 10, nullable = false)
    @Stereotype("MONEY")
    @PositiveOrZero(message = "El salario no puede ser negativo")
    private BigDecimal salario;

    @Column(length = 12, nullable = false)
    @Pattern(regexp = "\\d+", message = "El teléfono solo debe contener dígitos numéricos")
    private String telefono;

    @Required
    @PastOrPresent(message = "La fecha de contratación no puede ser mayor al día de hoy")
    private LocalDate fechaContratacion;

    @Column(length = 100, nullable = false)
    @Required(message="La contraseña es un campo obligatorio")
    @Stereotype("PASSWORD")
    private String contrasenna;
}