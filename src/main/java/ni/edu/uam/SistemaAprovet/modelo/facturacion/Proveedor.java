package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter @Setter
@View(members =
        "Datos Principales {" +
                "nombre, ruc;" +
                "telefono, contacto;" +
                "}" +
                "Direccion {" +
                "direccion;" +
                "}"
)
public class Proveedor {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de la empresa/proveedor es obligatorio")
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s.,'-]*$",
            message = "Solo se permiten letras y algunos signos (.,'-) y debe iniciar con una letra"
    )
    private String nombre;

    @Column(length = 20, unique = true)
    @Required(message = "El RUC es obligatorio")
    @Pattern(regexp = "\\d+", message = "El RUC solo debe contener dígitos numéricos")
    private String ruc;

    @Column(length = 15)
    @Required
    @Pattern(regexp = "\\d+", message = "El teléfono solo debe contener dígitos numéricos")
    private String telefono;

    @Column(length = 60)
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s.,'-]*$",
            message = "Solo se permiten letras y algunos signos (.,'-) y debe iniciar con una letra"
    )
    private String contacto;

    @Stereotype("MEMO")
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    private String direccion;

}
