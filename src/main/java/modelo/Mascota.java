package modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter @Setter
public class Mascota {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_mascota;

    @Column(name = "nombre_mascota", length = 50, nullable = false)
    private String nombre;

    @Column(name = "edad_mascota", nullable = false)
    private char sexo;

    @Column(name = "fecha_nacimiento_mascota", nullable = false)
    private Date fecha_nacimiento;

    @Column(name = "raza_mascota", length = 50, nullable = false)
    private String Raza;
}
