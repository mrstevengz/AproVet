package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter
public class Detalle {

    int cantidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    Producto producto;
}
