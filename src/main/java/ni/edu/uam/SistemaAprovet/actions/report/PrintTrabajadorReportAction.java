package ni.edu.uam.SistemaAprovet.actions.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ni.edu.uam.SistemaAprovet.modelo.core.Mascota;
import ni.edu.uam.SistemaAprovet.modelo.core.Trabajador;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import java.util.*;

public class PrintTrabajadorReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        EntityManager em = XPersistence.getManager();

        // Obtener todos los pedidos
        List<Trabajador> trabajadores = em
                .createQuery("from Trabajador ", Trabajador.class)
                .getResultList();

        return new JRBeanCollectionDataSource(trabajadores);
    }

    @Override
    protected String getJRXML() throws Exception {
        return "ReporteTrabajador.jrxml";  // archivo jrxml
    }

    @Override
    protected Map getParameters() throws Exception {
        return null; // no usamos parámetros por ahora
    }
}