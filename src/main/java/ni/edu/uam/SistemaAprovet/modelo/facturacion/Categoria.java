package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Categoria {
    // ----- PK -----
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_categoria;

    // ----- Demas Campos -----

    @Column(name = "nombre_categoria", length = 50, nullable = false)
    @Required
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 50, message = "El nombre de la categoría no puede superar 50 caracteres")
    private String nombre;

    @Column(name = "afecta_inventario", nullable = false)
    @NotNull(message = "Debe indicar si la categoría afecta inventario")
    private Boolean afectaInventario;

    @Column(name = "descripcion_categoria", length = 50)
    @Size(max = 50, message = "La descripción de la categoría no puede superar 50 caracteres")
    private String descripcion;

    @Column(name = "activo", nullable = false)
    @NotNull(message = "Debe indicar si la categoría está activa")
    private Boolean activo = true;
}
