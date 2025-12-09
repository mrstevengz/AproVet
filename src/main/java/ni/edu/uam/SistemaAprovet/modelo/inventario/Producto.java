package ni.edu.uam.SistemaAprovet.modelo.inventario;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter

@View(members =
        "Datos Generales {" +
                "codigo, categoria;" +
                "nombre;" +
                "precioVenta;" +
                "}" +
                "Descripcion {" +
                "descripcion;" +
                "}" +
                "Inventario {" +
                "inventario;" +
                "}"
)


@View(name = "Simple", members = "codigo, nombre, precioVenta")

public class Producto {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 20, unique = true, nullable = false)
    @Required(message = "El código es obligatorio")
    private String codigo;

    @Column(length = 100, nullable = false)
    @Required(message = "El nombre del producto es obligatorio")
    @SearchKey
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombre")
    @Required(message = "La categoría es obligatoria")
    private Categoria categoria;

    @Stereotype("MONEY")
    @Column(name = "precio_venta", nullable = false)
    @Required
    private BigDecimal precioVenta;

    @Stereotype("MEMO")
    private String descripcion;

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Inventario inventario;
}