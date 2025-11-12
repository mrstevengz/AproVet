package ni.edu.uam.Aprovet.run.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id_Trabajador",  unique = true, nullable=false)
    private long id_Trabajador;

    @Required
    @Column(name= "nombre_Trabajador", length=50, nullable=false)
    private String nombre;

    @Required
    @Column(name="apellido_Trabajador", length=50, nullable=false)
    private String apellido;

    @Required
    @Column(name="cedula_Trabajador", length=20, nullable=false, unique = true)
    private String cedula;

    @Required
    @Column(name= "telefono_Trabajador",  length=15, nullable=false, unique = true)
    private String telefono;

    @Required
    @Column(name="estado_Trabajador",   length=15)
    private String estado;

    @Required
    @Column(name="puesto_Trabajador", length=20)
    private String puesto;
}