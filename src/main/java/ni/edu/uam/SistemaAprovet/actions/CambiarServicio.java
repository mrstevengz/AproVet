package ni.edu.uam.SistemaAprovet.actions;

import org.openxava.actions.OnChangePropertyBaseAction;

public class CambiarServicio extends OnChangePropertyBaseAction {

    @Override
    public void execute() throws Exception {

        boolean esServicio = Boolean.TRUE.equals(getNewValue());

        if (esServicio) {
            // ? MODO SERVICIO: habilito servicio, deshabilito producto

            // Servicio (referencia + subvista)
            getView().setEditable("servicio", true);
            getView().getSubview("servicio").setEditable(true);

            // Producto (referencia + subvista)
            getView().setEditable("producto", false);
            getView().getSubview("producto").setEditable(false);
            getView().setValue("producto", null);   // limpio producto

        } else {
            // ? MODO PRODUCTO: habilito producto, deshabilito servicio

            // Producto (referencia + subvista)
            getView().setEditable("producto", true);
            getView().getSubview("producto").setEditable(true);

            // Servicio (referencia + subvista)
            getView().setEditable("servicio", false);
            getView().getSubview("servicio").setEditable(false);
            getView().setValue("servicio", null);   // limpio servicio
        }
    }
}