package ni.edu.uam.SistemaAprovet.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Cliente {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de cliente es obligatorio")
    private String nombre;

    @Column(length = 60, nullable = false)
    @Required(message = "El apellido de cliente es obligatorio")
    private String apellido;

    @Column(length = 16, nullable = false, unique = true)
    @Required(message = "La cédula es obligatoria")
    private String cedula;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String direccion;
}
