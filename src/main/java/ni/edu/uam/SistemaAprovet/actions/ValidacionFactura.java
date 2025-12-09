package ni.edu.uam.SistemaAprovet.actions;

import lombok.Setter;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.openxava.util.Messages;
import org.openxava.validators.IValidator;

import java.math.BigDecimal;

@Setter
public class ValidacionFactura implements IValidator {
    // Setters para inyeccion por OpenXava
    private boolean esServicio;
    private Producto producto;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal cantidad;

    @Override
    public void validate(Messages errors) throws Exception {

        // Validacion comun
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("precio_obligatorio_mayor_cero");
        }
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("cantidad_obligatoria_mayor_cero");
        }

        if (esServicio) {
            // Servicio: debe tener descripcion, NO producto
            if (descripcion == null || descripcion.trim().isEmpty()) {
                errors.add("descripcion_servicio_obligatoria");
            }
            if (producto != null) {
                errors.add("no_debe_indicar_producto_en_servicio");
            }

        } else {

            if (producto == null) {
                errors.add("producto_obligatorio");
            }
            if (descripcion != null && !descripcion.trim().isEmpty()) {
                errors.add("no_debe_indicar_descripcion_en_producto");
            }
        }
    }
}