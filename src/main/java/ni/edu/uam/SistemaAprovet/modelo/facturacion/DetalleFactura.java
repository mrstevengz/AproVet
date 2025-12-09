package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import ni.edu.uam.SistemaAprovet.modelo.facturacion.Servicio;
import org.openxava.annotations.*;


import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;

@Embeddable
@Getter @Setter
public class DetalleFactura {


    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Simple")
    private Producto producto;


    @ManyToOne(fetch = FetchType.LAZY)
    private Servicio servicio;

    @Required
    private Integer cantidad;

    @Required
    @Stereotype("MONEY")
    private BigDecimal precioUnitario;

    // =====================
    //  CÁLCULO SUBTOTAL
    // =====================

    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public BigDecimal getSubtotal() {
        if (cantidad == null || precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }

    // Validación: Debe elegir Producto O Servicio, pero no ambos vacíos (opcional)
    @AssertTrue(message = "Debe seleccionar un Producto o un Servicio")
    private boolean isProductoOServicio() {
        return producto != null || servicio != null;
    }
}