package es.unican.iotguardian.activities.riesgos;

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.Riesgo;

/**
 * Interfaz que define los métodos que deben ser implementados por el presentador y la vista
 * de la pestaña Riesgos.
 */
public interface IRiesgosContract {

    interface Presenter {
        /**
         * Inicializa las DAO y los riesgos de la vista.
         */
        void init();

        /**
         * Devuelve los datos con todas los riesgos que se encuentran
         * la base de datos.
         * @return Todos los riesgos.
         */
        List<Riesgo> getRiesgos();
    }

    interface View {
        /**
         * Devuelve una instancia de MyApplication.
         * @return MyApplication.
         */
        MyApplication getMyApplication();
    }
}
