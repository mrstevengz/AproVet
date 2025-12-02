package ni.edu.uam.SistemaAprovet.modelo.facturacion;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import ni.edu.uam.SistemaAprovet.modelo.core.Cliente;

@Entity
@Getter @Setter
public class Factura {

    // ----- PK -----
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_factura;

    // ----- FK -> Cliente -----
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    @DescriptionsList(descriptionProperties = "nombre,apellido") // muestra nombre y apellido
    @NotNull(message = "Debe seleccionar un cliente para la factura")
    private Cliente cliente;

    // ----- Fecha de registro -----
    @Column(name = "fecha_registro", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de registro es obligatoria")
    @Required
    private Date fecha_registro;

    // ----- Monto -----
    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "El monto de la factura es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    @Required
    private BigDecimal monto;
}
