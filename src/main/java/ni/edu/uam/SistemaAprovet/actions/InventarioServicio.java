package ni.edu.uam.SistemaAprovet.actions;

import ni.edu.uam.SistemaAprovet.modelo.inventario.Inventario;
import ni.edu.uam.SistemaAprovet.modelo.inventario.Producto;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class InventarioServicio {

    /**
     * Devuelve el inventario para el producto.
     * Si no existe, lo crea con valores por defecto.

     * Solo se toca STOCK automaticamente desde las compras.
     * stockMinimo y stockMaximo se dejan para que el usuario
     * los edite manualmente en el modulo de Inventario.
     */
    public static Inventario obtenerOCrearInventario(Producto producto) {
        EntityManager em = XPersistence.getManager();

        try {
            return em.createQuery(
                            "from Inventario i where i.producto = :prod",
                            Inventario.class)
                    .setParameter("prod", producto)
                    .getSingleResult();
        }
        catch (NoResultException ex) {
            Inventario inv = new Inventario();
            inv.setProducto(producto);

            // stock inicia en 0
            inv.setStock(0);

            // Valores por defecto validos (? 0 para que no fallen validaciones)
            inv.setStockMinimo(1);   // luego lo cambias en Inventario
            inv.setStockMaximo(1);   // idem

            em.persist(inv);
            return inv;
        }
    }
}