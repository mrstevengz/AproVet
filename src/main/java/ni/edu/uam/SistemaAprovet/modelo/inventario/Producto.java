package ni.edu.uam.SistemaAprovet.modelo.inventario;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.facturacion.Categoria;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.annotations.View;

import javax.persistence.*;

@Entity
@Getter @Setter
@View(name="Simple", members="nombre; categoria; unidad_medida") // vista sin inventario
public class Producto {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 100, nullable = false)
    @Required(message="El nombre es obligatorio")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Categoria categoria;

    @Column(length = 30)
    private String unidad_medida;

    @OneToOne(mappedBy = "producto",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Inventario inventario;

    @PrePersist
    private void crearInventarioSiNoExiste() {
        if (inventario == null) {
            inventario = new Inventario();
            inventario.setProducto(this);
            inventario.setStock(0);
            inventario.setStockMinimo(0);
            inventario.setStockMaximo(0);
        }
    }
}
