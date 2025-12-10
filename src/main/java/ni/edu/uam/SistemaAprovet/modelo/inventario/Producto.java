package ni.edu.uam.SistemaAprovet.modelo.inventario;

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
        "DatosGenerales {" +
                "codigo, categoria;" +
                "nombre;" +
                "Descripcion {" +
                "descripcion;" +
                "}" +
                "Inventario {" +
                "inventario;" +
                "}"+
        "}"
)

@View(name = "Simple", members = "codigo, nombre")

public class Producto {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 20, unique = true, nullable = false)
    @Required(message = "El código es obligatorio")
    @Pattern(
            regexp = "^(?=.*\\p{L})(?=.*\\d)(?=.*-)[\\p{L}\\d-]+$",
            message = "El codigo debe contener letras, números y al menos un guion (-), y solo puede usar letras, números y guiones"
    )
    private String codigo;

    @Column(length = 100, nullable = false)
    @Required(message = "El nombre del producto es obligatorio")
    @SearchKey
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s.,'-]*$",
            message = "Solo se permiten letras y algunos signos (.,'-) y debe iniciar con una letra"
    )
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombre")
    @Required(message = "La categoría es obligatoria")
    private Categoria categoria;


    @Stereotype("MEMO")
    private String descripcion;

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Inventario inventario;
}