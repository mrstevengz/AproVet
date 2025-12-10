package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero; // <--- 1. IMPORTAR ESTO
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
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    private String nombre;

    @Column(nullable = false)
    @Required
    @Stereotype("MONEY")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @Stereotype("MEMO")
    private String descripcion;
}