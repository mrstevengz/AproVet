package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
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
public class DetalleCompra {

    @Id
    @Hidden
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    private String oid;

    // ===== RELACIÓN CON COMPRA (MAESTRO) =====
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compra")
    private Compra compra;

    // ===== PRODUCTO =====
    @ManyToOne(optional = false)
    @Required
    private Producto producto;

    @Required
    private Integer cantidad;

    @Required
    @Stereotype("MONEY")
    @Column(name = "precio_unitario", nullable = false)
    private Float precioUnitario;

    // ==============
    //  SUBTOTAL
    // ==============
    @ReadOnly
    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public Float getSubtotal() {
        if (cantidad == null || precioUnitario == null) return 0f;
        return cantidad * precioUnitario;
    }

    // =================================================
    //   VALIDACIONES + CONTROL DE STOCK (COMPRA)
    //   (igual estilo que DetalleFactura)
    // =================================================

    @PrePersist
    @PreUpdate
    private void validarYAplicarStockCompra() {

        // ---- 1) Validar producto ----
        if (producto == null) {
            throw new XavaException("Debe seleccionar un producto en el detalle de la compra.");
        }

        // ---- 2) Validar cantidad y precio ----
        if (cantidad == null || cantidad <= 0) {
            throw new XavaException("La cantidad debe ser mayor que cero.");
        }
        if (precioUnitario == null || precioUnitario <= 0) {
            throw new XavaException("El precio unitario debe ser mayor que cero.");
        }

        // ---- 3) Buscar inventario de ese producto ----
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

        // COMPRA => AUMENTA STOCK
        inv.setStock(stockActual + cantidad);
    }

    // =================================================
    //   DEVOLVER STOCK AL BORRAR LA COMPRA
    // =================================================

    @PreRemove
    private void devolverStockSiBorroCompra() {
        if (producto == null || cantidad == null || cantidad <= 0) return;

        EntityManager em = XPersistence.getManager();

        List<Inventario> lista = em.createQuery(
                        "from Inventario i where i.producto = :prod",
                        Inventario.class)
                .setParameter("prod", producto)
                .getResultList();

        if (lista.isEmpty()) return; // nada que revertir

        Inventario inv = lista.get(0);
        Integer actual = inv.getStock();
        int stockActual = (actual == null) ? 0 : actual;

        // Deshacer la compra => RESTAR lo que antes sumamos
        inv.setStock(stockActual - cantidad);
    }
}
