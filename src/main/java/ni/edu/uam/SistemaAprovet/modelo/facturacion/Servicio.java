package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Servicio {

    // ----- PK -----
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_servicio;


    @Column(name = "nombre_servicio", length = 50, nullable = false)
    @Required
    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 50, message = "El nombre del servicio no puede superar 50 caracteres")
    private String nombre;

    @Column(name = "descripcion_servicio", length = 50)
    @Size(max = 50, message = "La descripción del servicio no puede superar 50 caracteres")
    private String descripcion;



}
