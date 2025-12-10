package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Getter
@Setter

@View(name = "Short", members =
        "nombreMascota"
)
public class Mascota {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de la mascota es obligatorio")
    @Pattern(regexp = ".*[^0-9].*", message = "El nombre no puede contener solo números, debe incluir letras")
    private String nombreMascota;

    public enum Genero {MACHO, HEMBRA}

    @Column(nullable = false)
    @Required(message = "El genero de mascota es obligatorio")
    private Genero generoMascota;

    @Required(message = "La fecha de nacimiento es obligatoria")
    @PastOrPresent(message = "La fecha de nacimiento no puede ser mayor al día de hoy")
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Short")
    @DescriptionsList(descriptionProperties = "nombreCliente, apellidoCliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Raza raza;
}
