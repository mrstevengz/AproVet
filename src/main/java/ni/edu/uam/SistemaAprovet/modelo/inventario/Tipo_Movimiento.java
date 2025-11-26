package ni.edu.uam.SistemaAprovet.modelo.inventario;

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
public class Tipo_Movimiento {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    public enum Tipo {Entrada, Salida}
    @Column(nullable = false)
    @Required(message = "El tipo de movimiento es obligatorio")
    private Tipo tipo;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre del tipo de movimiento es obligatorio")
    private String nombre;
}
