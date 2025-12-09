package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Compra {

    // =====================
    //  IDENTIFICADOR
    // =====================
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    // =====================
    //  DATOS BÁSICOS
    // =====================

    @ManyToOne(optional = false)
    @DescriptionsList   // para elegir el proveedor en combo
    private Proveedor proveedor;

    @Required
    @Stereotype("DATE")
    private LocalDate fechaCompra = LocalDate.now();

    @Stereotype("TEXT_AREA")
    private String descripcion;

    // Total de la compra (se recalcula desde los detalles)
    @Stereotype("MONEY")
    @Column(nullable = false)
    private Float total = 0f;      // ?? valor por defecto para permitir crear sin detalles

    // =====================
    //  DETALLES DE COMPRA
    // =====================

    @OneToMany(mappedBy = "compra",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ListProperties("producto.nombre, cantidad, precioUnitario, subtotal")
    private List<DetalleCompra> detalleCompra = new ArrayList<>();

    // =====================
    //  MÉTODOS DE APOYO
    // =====================

    /**
     * Recalcula el total sumando los subtotales de todos los detalles.
     * (La estás usando indirectamente desde DetalleCompra.beforeSave()).
     */
    public void recalcularTotalDesdeDetalles() {
        float t = 0f;
        if (detalleCompra != null) {
            for (DetalleCompra d : detalleCompra) {
                Float sub = d.getSubtotal();
                if (sub != null) t += sub;
            }
        }
        this.total = t;
    }
}