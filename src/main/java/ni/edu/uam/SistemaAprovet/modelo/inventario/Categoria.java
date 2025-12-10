package ni.edu.uam.SistemaAprovet.modelo.inventario;

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
import javax.validation.constraints.Pattern; // <--- IMPORTANTE IMPORTAR ESTO
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
    private String idCategoria;

    @Column(name = "nombre_categoria", length = 50, nullable = false)
    @Required
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 50, message = "El nombre de la categoría no puede superar 50 caracteres")
    // Esta expresión regular obliga a que haya al menos un caracter que NO sea número
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    private String nombre;

    @Column(name = "descripcion_categoria", length = 50)
    @Size(max = 50, message = "La descripción de la categoría no puede superar 50 caracteres")
    // Lo mismo para la descripción
    @Pattern(regexp = ".*[^0-9].*", message = "La descripción no puede contener solo números")
    private String descripcion;

}