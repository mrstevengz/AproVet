package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.annotations.View;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@View(name = "Short", members =
        "nombreCliente, apellidoCliente"
)
public class Cliente {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oidCliente;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de cliente es obligatorio")
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s.,'-]*$",
            message = "Solo se permiten letras y algunos signos (.,'-) y debe iniciar con una letra"
    )
    private String nombreCliente;

    @Column(length = 60, nullable = false)
    @Required(message = "El apellido de cliente es obligatorio")
    @Pattern(regexp = ".*[^0-9].*", message = "El apellido no puede contener solo números, debe incluir letras")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s.,'-]*$",
            message = "Solo se permiten letras y algunos signos (.,'-) y debe iniciar con una letra"
    )
    private String apellidoCliente;

    @Column(length = 16, nullable = false, unique = true)
    @Required(message = "La cédula es obligatoria")
    @Pattern(
            regexp = "^(?=.*\\p{L})(?=.*\\d)(?=.*-)[\\p{L}\\d-]+$",
            message = "La cédula debe contener letras, números y al menos un guion (-), y solo puede usar letras, números y guiones"
    )
    private String cedulaCliente;

    @Column(length = 15)
    @Pattern(regexp = "\\d+", message = "El telefono solo puede contener dígitos y no puede ser negativo")
    private String telefonoCliente;

    @Column(length = 100)
    @Pattern(regexp = ".*[^0-9].*", message = "El apellido no puede contener solo números, debe incluir letras")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s.,'-]*$",
            message = "Solo se permiten letras y algunos signos (.,'-) y debe iniciar con una letra"
    )
    private String direccionCliente;
}
