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
    private String nombreCliente;

    @Column(length = 60, nullable = false)
    @Required(message = "El apellido de cliente es obligatorio")
    private String apellidoCliente;

    @Column(length = 16, nullable = false, unique = true)
    @Required(message = "La cédula es obligatoria")
    private String cedulaCliente;

    @Column(length = 15)
    private String telefonoCliente;

    @Column(length = 100)
    private String direccionCliente;
}
