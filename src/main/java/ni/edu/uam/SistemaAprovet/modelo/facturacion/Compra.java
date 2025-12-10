package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
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
    @DescriptionsList
    private Proveedor proveedor;

    @Required
    @Stereotype("DATE")
    @PastOrPresent(message = "La fecha de compra no puede ser mayor al día de hoy")
    private LocalDate fechaCompra = LocalDate.now();

    @Stereotype("TEXT_AREA")
    private String descripcion;


    // =====================
    //  DETALLES DE COMPRA
    // =====================
    @OneToMany(mappedBy = "compra",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ListProperties("producto.nombre, cantidad, precioUnitario, subtotal")
    private List<DetalleCompra> detalleCompra = new ArrayList<>();

    @Money
    @Depends("detalleCompra.subtotal")
    @Transient   // no se guarda en la BD
    public float getMonto() {
        float total = 0f;
        if (detalleCompra != null) {
            for (DetalleCompra d : detalleCompra) {
                Float sub = d.getSubtotal();
                if (sub != null) total += sub;
            }
        }
        return total;
    }
}
