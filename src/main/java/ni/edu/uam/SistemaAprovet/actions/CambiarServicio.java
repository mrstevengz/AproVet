package ni.edu.uam.SistemaAprovet.actions;


import org.openxava.actions.OnChangePropertyBaseAction;

public class CambiarServicio extends OnChangePropertyBaseAction {

    @Override
    public void execute() throws Exception {

        Boolean esServicio = (Boolean) getNewValue();
        if (esServicio == null) esServicio = Boolean.FALSE;

        if (esServicio) {
            // ES servicio ? bloquear producto, habilitar descripcion
            getView().setEditable("producto", false);
            getView().setValue("producto", null);
            getView().setEditable("servicio", true);



        } else {
            // NO es servicio ? habilitar producto, bloquear descripcion
            getView().setEditable("producto", true);
            getView().setEditable("servicio", false);
            getView().setValue("servicio", null);
        }
    }
}