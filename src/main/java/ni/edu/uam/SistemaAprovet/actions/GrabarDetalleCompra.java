package ni.edu.uam.SistemaAprovet.actions;

import ni.edu.uam.SistemaAprovet.modelo.facturacion.Compra;
import ni.edu.uam.SistemaAprovet.modelo.facturacion.DetalleCompra;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import org.openxava.actions.SaveAction;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class GrabarDetalleCompra extends SaveAction {

    @Override
    public void execute() throws Exception {

        // 1) Guardar el detalle normalmente (INSERT DetalleCompra)
        super.execute();

        // 2) Obtener la clave del detalle recien guardado
        Map<String, Object> key = getView().getKeyValues();
        if (key == null || key.isEmpty()) return;

        String oid = (String) key.get("oid");
        if (oid == null) return;

        EntityManager em = XPersistence.getManager();

        // 3) Recuperar el DetalleCompra desde la BD
        DetalleCompra detalle = em.find(DetalleCompra.class, oid);
        if (detalle == null) return;

        // ====================================================
        // 3) Recalcular TOTAL de la compra (SUM en la BD)
        // ====================================================
        Compra compra = detalle.getCompra();
        if (compra != null) {

            // Nos aseguramos que la compra este gestionada
            compra = em.merge(compra);

            Number totalN = (Number) em.createQuery(
                            "select sum(d.cantidad * d.precioUnitario) " +
                                    "from DetalleCompra d where d.compra = :compra"
                    )
                    .setParameter("compra", compra)
                    .getSingleResult();

            float total = (totalN == null) ? 0f : totalN.floatValue();
            compra.setTotal(total);   // total en Compra debe ser Float o BigDecimal compatible
        }

        // ====================================================
        // 4) Actualizar INVENTARIO (aumentar stock)
        // ====================================================
        if (detalle.getProducto() != null && detalle.getCantidad() != null) {

            TypedQuery<Inventario> qi = em.createQuery(
                    "select i from Inventario i where i.producto = :producto",
                    Inventario.class
            );
            qi.setParameter("producto", detalle.getProducto());
            List<Inventario> lista = qi.getResultList();

            Inventario inv;
            if (lista.isEmpty()) {
                // Si no existe inventario para ese producto, lo creamos
                inv = new Inventario();
                inv.setProducto(detalle.getProducto());
                inv.setStock(0);
                inv.setStockMinimo(0);
                inv.setStockMaximo(0);
                em.persist(inv);
            } else {
                inv = lista.get(0);
            }

            // Aseguramos que este gestionado
            inv = em.merge(inv);

            int actual = (inv.getStock() == null) ? 0 : inv.getStock();
            inv.setStock(actual + detalle.getCantidad());

            // Guardamos la relacion por si quieres mostrar inventario en el detalle
            detalle.setInventario(inv);
        }

        // 5) Forzamos envio a la BD
        em.flush();

        // 6) Refrescamos la vista raiz (Compra) para ver el total actualizado en pantalla
        getView().getRoot().refresh();
    }
}