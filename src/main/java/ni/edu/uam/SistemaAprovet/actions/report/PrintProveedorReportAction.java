package ni.edu.uam.SistemaAprovet.actions.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ni.edu.uam.SistemaAprovet.modelo.clinica.Receta;
import ni.edu.uam.SistemaAprovet.modelo.facturacion.Proveedor;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class PrintProveedorReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        EntityManager em = XPersistence.getManager();

        // Obtener todos los pedidos
        List<Proveedor> proveedores = em
                .createQuery("from Proveedor ", Proveedor.class)
                .getResultList();

        return new JRBeanCollectionDataSource(proveedores);
    }

    @Override
    protected String getJRXML() throws Exception {
        return "ReporteProveedor.jrxml";  // archivo jrxml
    }

    @Override
    protected Map getParameters() throws Exception {
        return null; // no usamos parámetros por ahora
    }
}