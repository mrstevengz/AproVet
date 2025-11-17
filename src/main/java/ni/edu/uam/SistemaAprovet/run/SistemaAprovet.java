package ni.edu.uam.SistemaAprovet.run;

import org.openxava.util.*;

import java.util.Locale;

/**
 * Ejecuta esta clase para arrancar la aplicación.
 *
 * Con OpenXava Studio/Eclipse: Botón derecho del ratón > Run As > Java Application
 */

public class SistemaAprovet {

	public static void main(String[] args) throws Exception {
		// DBServer.start("SistemaAprovet-db"); // Para usar tu propia base de datos comenta esta línea y configura src/main/webapp/META-INF/context.xml
		Locale.setDefault(new Locale("es", "NI")); //
        AppServer.run("SistemaAprovet"); // Usa AppServer.run("") para funcionar en el contexto raíz
	}

}
