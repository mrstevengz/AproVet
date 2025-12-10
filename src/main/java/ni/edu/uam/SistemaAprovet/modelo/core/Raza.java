package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.TextArea;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter @Setter
public class Raza {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Especie especie;

    @Column(length = 60, nullable = false)
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    private String nombre;

}
