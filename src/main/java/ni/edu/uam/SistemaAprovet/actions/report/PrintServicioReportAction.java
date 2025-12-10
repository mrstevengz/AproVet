package ni.edu.uam.SistemaAprovet.actions.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ni.edu.uam.SistemaAprovet.modelo.core.Cliente;
import ni.edu.uam.SistemaAprovet.modelo.facturacion.Servicio;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class PrintServicioReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        EntityManager em = XPersistence.getManager();

        // Obtener todos los pedidos
        List<Servicio> servicios = em
                .createQuery("from Servicio ", Servicio.class)
                .getResultList();

        return new JRBeanCollectionDataSource(servicios);
    }

    @Override
    protected String getJRXML() throws Exception {
        return "ReporteServicio.jrxml";  // archivo jrxml
    }

    @Override
    protected Map getParameters() throws Exception {
        return null; // no usamos parámetros por ahora
    }
}