package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.math.BigDecimal; // Recomendado para dinero

@Embeddable
@Getter @Setter
public class DetalleCompra {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Simple")
    private Producto producto;

    @Required
    private Integer cantidad;

    @Required
    @Stereotype("MONEY")
    private BigDecimal precioUnitario;

    // =====================
    //  PROPIEDAD CALCULADA
    // =====================

    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public BigDecimal getSubtotal() {
        if (cantidad == null || precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }
}
