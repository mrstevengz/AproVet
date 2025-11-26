package ni.edu.uam.SistemaAprovet.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.annotations.TextArea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Especie {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")

    private String oid;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de la especie es requerido.")
    private String nombre;

    @Column(length = 60)
    private String nombre_cientifico;

    @TextArea
    private String descripcion;

    @Column(nullable = false)
    private boolean activo;

}
