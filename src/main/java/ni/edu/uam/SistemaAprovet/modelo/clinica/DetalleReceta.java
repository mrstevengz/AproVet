package ni.edu.uam.SistemaAprovet.modelo.clinica;


import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.openxava.annotations.ReferenceView;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter
public class DetalleReceta {
    private String frecuencia;
    private int duracion;
    private double dosis;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Simple")
    private Producto producto;
}
