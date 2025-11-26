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
@Getter @Setter
public class Tipo_Movimiento {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private int oid;

    @Column(length = 50, nullable = false)
    @Required(message = "El tipo es obligatorio")
    private String tipo;

    @Column(length = 50, nullable = false)
    @Required(message = "El nombre es obligatorio")
    private String nombre;
}
