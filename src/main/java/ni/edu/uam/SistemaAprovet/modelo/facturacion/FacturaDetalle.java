package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import lombok.Getter;
import lombok.Setter;

import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Required;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
public class FacturaDetalle {

    // ----- PK: id_Detalle (int) -----
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    // ----- FK -> Factura (id_factura : int) -----
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_factura", nullable = false)
    @DescriptionsList(descriptionProperties = "id_factura")
    @NotNull(message = "Debe seleccionar una factura")
    private Factura factura;

    // ----- FK -> Servicio (id_servicio : int) -----
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_servicio", nullable = false)
    @DescriptionsList(descriptionProperties = "nombre")
    @NotNull(message = "Debe seleccionar un servicio")
    private Servicio servicio;

    // ----- FK -> Producto (id_producto : int) -----
    @ManyToOne(optional = true)
    @JoinColumn(name = "oid", nullable = false)
    @DescriptionsList(descriptionProperties = "nombre")
    @NotNull(message = "Debe seleccionar un producto")
    private Producto producto;

    // ----- cantidad : int  (CHECK CantidadMinima) -----
    @Column(name = "cantidad", nullable = true)
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Required
    private Integer cantidad;

    // ----- precio_unitario : float (CHECK PrecioUnitarioMinimo) -----
    @Column(name = "precio_unitario", nullable = false)
    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario mínimo es 0.01")
    @Required
    private Float precioUnitario;

    // ----- subtotal : float (CHECK SubtotalMinimo) -----

}