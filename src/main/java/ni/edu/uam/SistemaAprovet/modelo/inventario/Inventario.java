package ni.edu.uam.SistemaAprovet.modelo.inventario;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;

@Entity @Setter @Getter
public class Inventario {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @OneToOne
    @JoinColumn(name = "producto_oid", nullable = false, unique = true)
    private Producto producto;

    @Column(nullable = false)
    @Required(message = "El stock es obligatorio")
    private Integer stock;

    @Column(nullable = false)
    @Required(message = "El stock mínimo es obligatorio")
    private Integer stockMinimo;

    @Column(nullable = false)
    @Required(message = "El stock máximo es obligatorio")
    private Integer stockMaximo;
}
