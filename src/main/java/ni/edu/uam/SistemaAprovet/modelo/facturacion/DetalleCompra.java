package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.math.BigDecimal; // Recomendado para dinero

@Entity
@Getter @Setter
public class DetalleCompra {
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Compra compra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ReferenceView("Simple")
    private Producto producto;

    @Required
    private Integer cantidad;

    @Required
    @Stereotype("MONEY")
    private float precioUnitario =0;

    // =====================
    //  PROPIEDAD CALCULADA
    // =====================

    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public float getSubtotal() {
        if (cantidad == null || precioUnitario == 0) return 0;
        return cantidad * precioUnitario;
    }
}
