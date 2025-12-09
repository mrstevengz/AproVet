package ni.edu.uam.SistemaAprovet.modelo.core;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Required;
import org.openxava.util.Messages;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Mascota {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 60, nullable = false)
    @Required(message = "El nombre de la mascota es obligatorio")
    private String nombre;

    public enum Genero {MACHO, HEMBRA}

    @Column(nullable = false)
    @Required(message = "El genero de mascota es obligatorio")
    private Genero genero;

    @Required
    private LocalDate fecha_nacimiento;

    // --- Método auxiliar ---
    public boolean verificarFechaNacimiento() {
        return fecha_nacimiento == null || !fecha_nacimiento.isAfter(LocalDate.now());
    }

    // --- Validación a nivel de entidad (app, no BD) ---
    public void validate(Messages errors) {
        if (!verificarFechaNacimiento()) {
            errors.add("La fecha de nacimiento no puede ser mayor que la fecha de hoy.");
        }
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Simple")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Raza raza;
}
