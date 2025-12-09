package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@View(members =
        "nombre;" +
                "precio;" +
                "descripcion"
)
public class Servicio {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre del servicio es obligatorio")
    private String nombre;

    @Column(nullable = false)
    @Required
    @Stereotype("MONEY")
    private BigDecimal precio;

    @Stereotype("MEMO")
    private String descripcion;
}

