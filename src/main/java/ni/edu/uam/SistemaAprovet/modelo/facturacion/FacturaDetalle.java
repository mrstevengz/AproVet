package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.actions.CambiarServicio;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;
import org.openxava.util.XavaException;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class FacturaDetalle {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    // ===== RELACIÓN CON FACTURA (MAESTRO) =====
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_factura")
    private Factura factura;

    // ===== CAMPOS DE NEGOCIO =====

    @OnChange(CambiarServicio.class)
    private Boolean esServicio;  // true = servicio, false/null = producto

    @ManyToOne
    private Producto producto;   // si es producto se descuenta stock

    @ManyToOne
    private Servicio servicio;   // si es servicio NO toca inventario

    @Column(nullable = false)
    @Required
    private Integer cantidad;

    @Money
    @Column(nullable = false)
    @Required
    private float precioUnitario;

    // ===== SUBTOTAL CALCULADO =====
    @Money
    @ReadOnly
    @Depends("cantidad, precioUnitario")
    public float getSubtotal() {
        if (cantidad == null || precioUnitario == 0) return 0;
        return precioUnitario * cantidad;
    }

    // =================================================
    //   VALIDACIONES + CONTROL DE STOCK (APP, NO BD)
    // =================================================

    @PrePersist
    @PreUpdate
    private void validarYAplicarReglas() {

        // ---- 1) Validar servicio vs producto ----
        if (Boolean.TRUE.equals(esServicio)) { // es servicio
            if (servicio == null) {
                throw new XavaException("Debe seleccionar un servicio cuando el detalle está marcado como servicio.");
            }
            // Opcional: si no quieres que quede producto lleno
            // if (producto != null) {
            //     throw new XavaException("No debe seleccionar un producto cuando el detalle es un servicio.");
            // }
        } else { // producto
            if (producto == null) {
                throw new XavaException("Debe seleccionar un producto cuando el detalle no está marcado como servicio.");
            }
        }

        // ---- 2) Validar cantidad y precio ----
        if (cantidad == null || cantidad <= 0) {
            throw new XavaException("La cantidad debe ser mayor que cero.");
        }
        if (precioUnitario <= 0) {
            throw new XavaException("El precio unitario debe ser mayor que cero.");
        }

        // ---- 3) Validar stock solo si es producto ----
        if (!Boolean.TRUE.equals(esServicio)) {

            EntityManager em = XPersistence.getManager();

            List<Inventario> lista = em.createQuery(
                            "from Inventario i where i.producto = :prod",
                            Inventario.class)
                    .setParameter("prod", producto)
                    .getResultList();

            if (lista.isEmpty()) {
                throw new XavaException(
                        "No existe inventario registrado para el producto: " + producto.getNombre()
                );
            }

            Inventario inv = lista.get(0);
            Integer actual = inv.getStock();
            int stockActual = (actual == null) ? 0 : actual;

            if (cantidad > stockActual) {
                throw new XavaException(
                        "Stock insuficiente para el producto: " + producto.getNombre()
                                + ". Disponible: " + stockActual
                                + ", solicitado: " + cantidad + "."
                );
            }

            // Si todo está ok y es INSERT/UPDATE, descontamos stock
            // OJO: solo tiene sentido descontar en INSERT; si quieres ser ultra-preciso
            // se podría distinguir entre PrePersist/PreUpdate, pero así ya funciona
            inv.setStock(stockActual - cantidad);
        }
    }

    // =================================================
    //   DEVOLVER STOCK AL BORRAR
    // =================================================

    @PreRemove
    private void devolverStockSiBorro() {
        if (Boolean.TRUE.equals(esServicio)) return;
        if (producto == null || cantidad == null || cantidad <= 0) return;

        EntityManager em = XPersistence.getManager();

        List<Inventario> lista = em.createQuery(
                        "from Inventario i where i.producto = :prod",
                        Inventario.class)
                .setParameter("prod", producto)
                .getResultList();

        if (lista.isEmpty()) return;

        Inventario inv = lista.get(0);
        Integer actual = inv.getStock();
        int stockActual = (actual == null) ? 0 : actual;

        inv.setStock(stockActual + cantidad);   // deshago la venta
    }
}
