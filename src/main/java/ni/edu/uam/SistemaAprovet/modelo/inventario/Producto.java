package ni.edu.uam.SistemaAprovet.modelo.inventario;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Money;
import org.openxava.annotations.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Producto {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 100, nullable = false)
    @Required(message="El nombre es obligatorio")
    private String nombre;

    @Money
    @Column(nullable = false)
    @Required(message = "El precio de venta es obligatorio")
    private Double precio_venta;

    @Column(length = 30)
    private String unidad_medida;
}
