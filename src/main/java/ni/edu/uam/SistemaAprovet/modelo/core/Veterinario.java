package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Veterinario {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String idVeterinario;

    @Column(name= "numero_licencia", length = 20, nullable = false, unique = true)
    private String numeroLicencia;

    @Column(name= "especialidad", length = 15, nullable = false)
    private String especialidad;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Trabajador trabajador;

}
