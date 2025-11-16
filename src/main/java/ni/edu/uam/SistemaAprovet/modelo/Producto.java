package ni.edu.uam.SistemaAprovet.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Money;
import org.openxava.annotations.Required;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Producto {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "nombre_producto", length = 100, nullable = false)
    @Required(message = "El producto debe tener un nombre")
    private String nombre;

    @Money
    @Column(name = "precio_producto")
    @Required(message = "Se le debe asignar un precio al producto")
    private Double precio_venta;

    @Column(name = "unidad_medida", length = 30)
    private String unidad_medida;
}
