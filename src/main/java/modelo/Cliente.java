package modelo;

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
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Cliente {

    // ----- PK -----
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_cliente;

    // ----- Demás campos -----

    @Required
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    @Column(name = "nombre_cliente", length = 50, nullable = false)
    private String nombre;

    @Required
    @NotBlank(message = "El apellido del cliente es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar 50 caracteres")
    @Column(name = "apellido_cliente", length = 50, nullable = false)
    private String apellido;

    @Required
    @NotBlank(message = "La cédula del cliente es obligatoria")
    @Size(max = 18, message = "La cédula no puede superar 18 caracteres")
    @Column(name = "cedula_cliente", length = 18, nullable = false, unique = true)
    private String cedula;

    @Required
    @NotBlank(message = "El teléfono del cliente es obligatorio")
    @Size(max = 8, message = "El teléfono no puede superar 12 caracteres")
    @Column(name = "telefono_cliente", length = 12, nullable = false, unique = true)
    private String telefono;   // <-- aquí la corrección: minúscula

    @Required
    @NotBlank(message = "La dirección del cliente es obligatoria")
    @Size(max = 100, message = "La dirección no puede superar 100 caracteres")
    @Column(name = "direccion_cliente", length = 100, nullable = false)
    private String direccion;
}
