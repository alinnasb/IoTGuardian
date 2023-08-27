package es.unican.iotguardian.activities.riesgos.detail;

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.Control;

public interface IRiesgoDetailContract {
    interface View {

        MyApplication getMyApplication();
    }

    interface Presenter {

        void init();

        String getRiesgoName();

        String getRiesgoDesc();

        List<Control> getRiskControls();

        List<Control> getPerfilControls();
    }
}
