package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;

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

    @Column(length = 50)
    private String tipo_trabajador;

    @Column(length = 100, nullable = false)
    @Required(message="La contraseña es un campo obligatorio")
    private String contraseña;
}
