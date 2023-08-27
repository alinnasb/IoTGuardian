package es.unican.iotguardian.activities.dispositivos.detail;

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Riesgo;

/**
 * Interfaz que define los métodos que deben ser implementados por el presentador y la vista
 * del detalle de un dispositivo.
 */
public interface IDispositivoDetailContract {
    interface  View {

        MyApplication getMyApplication();
    }

    interface Presenter {

        void init();

        List<Riesgo> getDeviceRisks();

        String getDeviceIcono();

        String getDeviceName();

        String getDeviceCategory();

        void onAddDeviceClicked();

        boolean isDeviceAdded();

        List<DispositivoIoT> getPerfilDevices();
    }
}
