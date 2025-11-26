package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Veterinario {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_veterinario;

    @Column(name = "nombre_veterinario", length = 100, nullable = false)
    private String nombre;

    @Column(name= "apellido_veterinario", length = 100, nullable = false)
    private String apellido;

    @Column(name= "cedula_veterinario", length = 20, nullable = false, unique = true)
    private String cedula;

    @Column(name= "telefono_veterinario", length = 15, nullable = false)
    private String telefono;

    @Column(name = "direccion_veterinario", length = 200, nullable = false)
    private String direccion;

    @Column(name = "estado_veterinario", nullable = false)
    private Boolean estado;
}
