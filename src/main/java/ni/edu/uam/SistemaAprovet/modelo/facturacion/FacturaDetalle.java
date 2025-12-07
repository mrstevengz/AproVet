package ni.edu.uam.SistemaAprovet.modelo.facturacion;

import lombok.Getter;
import lombok.Setter;
import ni.edu.uam.SistemaAprovet.actions.CambiarServicio;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;

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
    private Servicio servicio;   // si es servicio NO toca inventario (ajusta el paquete)

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
    //   CALLBACKS: INVENTARIO SOLO PARA PRODUCTO
    // =================================================

    @PrePersist
    private void disminuirStockAlVender() {
        if (Boolean.TRUE.equals(esServicio)) return;      // es servicio -> no toca inventario
        if (producto == null || cantidad == null) return;

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
        inv.setStock(stockActual - cantidad);   // venta => baja stock
    }

    @PreRemove
    private void devolverStockSiBorro() {
        if (Boolean.TRUE.equals(esServicio)) return;
        if (producto == null || cantidad == null) return;

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
