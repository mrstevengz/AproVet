package ni.edu.uam.SistemaAprovet.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.openxava.actions.JasperReportBaseAction;

import java.util.Map;

/**
 * Acción para imprimir el reporte de Mascotas.
 * Este reporte utiliza un JRXML llamado "MascotasReport.jrxml"
 * ubicado dentro de /reports/ en el proyecto OpenXava.
 */
public class PrintMascotasReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {
        // Si el reporte no necesita datos dinámicos, usar un DataSource vacío
        return new JREmptyDataSource();
    }

    @Override
    protected String getJRXML() throws Exception {
        // Nombre del archivo .jrxml dentro de /reports/
        return "MascotasReport.jrxml";
    }

    @Override
    protected Map getParameters() throws Exception {
        Map<String, Object> params = new java.util.HashMap<>();

        // Fecha y hora del sistema
        params.put("fecha_emision", new java.util.Date());

        return params;
    }
}
