package es.unican.iotguardian.activities.main;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Interfaz que define los métodos que deben ser implementados por el presentador y la vista
 * en la pantalla principal de la aplicación.
 */
public interface IMainContract {

    interface Presenter {
        /**
         * Debe ser usado por la vista cuando se pulse el icono de "Apps" en el boton de
         * navegacion inferior.
         * Muestra el fragmento correspondiente a la pestaña "Apps".
         */
        void onNavAppsClicked();

        /**
         * Debe ser usado por la vista cuando se pulse el icono de "Riesgos" en el boton de
         * navegacion inferior.
         * Muestra el fragmento correspondiente a la pestaña "Riesgos".
         */
        void onNavRiesgosClicked();

        /**
         * Debe ser usado por la vista cuando se pulse el icono de "Controles" en el boton de
         * navegacion inferior.
         * Muestra el fragmento correspondiente a la pestaña "Controles".
         */
        void onNavControlesClicked();

        /**
         * Debe ser usado por la vista cuando se pulse el icono de "Perfil" en el boton de
         * navegacion inferior.
         * Muestra el fragmento correspondiente a la pestaña "Perfil".
         */
        void onNavPerfilClicked();
    }

    interface View {

        /**
         * Instancia y muestra en pantalla un fragmento poniendo el titulo indicado en la toolbar y
         * aplicando una sencilla animacion en el boton de navegacion inferior.
         * @param title Titulo que se desea que se muestre.
         */
        void showFragment(Fragment frg, @StringRes int title);
    }
}
