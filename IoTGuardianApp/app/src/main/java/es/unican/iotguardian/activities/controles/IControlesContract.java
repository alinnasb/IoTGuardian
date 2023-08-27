package es.unican.iotguardian.activities.controles;

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.Control;

/**
 * Interfaz que define los métodos que deben ser implementados por el presentador y la vista
 * de la pestaña Controles.
 */
public interface IControlesContract {
    interface Presenter {
        /**
         * Inicializa las DAO y los controles de la vista.
         */
        void init();

        /**
         * Devuelve los datos con todas los controles que se encuentran
         * la base de datos.
         * @return Todos los controles.
         */
        List<Control> getControles();

        List<Control> getPerfilControls();
    }

    interface View {
        /**
         * Devuelve una instancia de MyApplication.
         * @return MyApplication.
         */
        MyApplication getMyApplication();
    }
}
