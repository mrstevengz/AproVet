package ni.edu.uam.SistemaAprovet.modelo.inventario;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Movimiento_Inventario {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Required(message = "Debe indicar el inventario afectado")
    private Inventario inventario;

    @Column(nullable = false)
    @Required(message = "La cantidad es obligatoria")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Required(message = "Debe indicar el tipo de movimiento")
    private Tipo_Movimiento ref_tipo;

    @Column(nullable = false)
    @Required(message = "La fecha es obligatoria")
    private LocalDate fecha;


}
