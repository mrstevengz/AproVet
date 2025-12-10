package ni.edu.uam.SistemaAprovet.modelo.clinica;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Required; // Recomendado para que la UI marque el campo

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive; // <--- IMPORTANTE

@Embeddable
@Getter
@Setter
public class DetalleReceta {

    @Required(message = "La frecuencia es obligatoria")
    private String frecuencia;

    // @Positive obliga a que sea 1 o mayor (no permite 0 ni negativos)
    @Positive(message = "La duración debe ser un número positivo de días")
    @Required
    private int duracion;

    // @Positive obliga a que sea mayor a 0.0 (ej. 0.5, 1.0)
    @Positive(message = "La dosis debe ser mayor a 0")
    @Required
    private double dosis;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Simple")
    @Required(message = "Debe seleccionar un producto")
    private Producto producto;
}