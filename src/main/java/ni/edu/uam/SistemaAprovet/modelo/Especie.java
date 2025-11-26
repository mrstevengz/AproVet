package ni.edu.uam.SistemaAprovet.modelo;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter  @Setter
@Entity
public class Especie {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(nullable=false)
    private String id;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de la especie es requerido.")
    private String nombre;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre cientifico es requerido.")
    private String nommbre_Cientifico;

    @Column(length = 60, nullable = false)
    private String descripcion;

    @Column(length = 60, nullable = false)
    private boolean activo;

}
