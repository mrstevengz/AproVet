package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;

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
    private String nombre;

    @Column(length = 20, unique = true)
    @Required(message = "El RUC es obligatorio")
    private String ruc;

    @Column(length = 15)
    @Required
    private String telefono;

    @Column(length = 60)
    private String contacto;

    @Stereotype("MEMO")
    private String direccion;

}