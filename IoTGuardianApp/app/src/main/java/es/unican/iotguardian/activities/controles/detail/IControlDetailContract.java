package es.unican.iotguardian.activities.controles.detail;

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.Riesgo;

public interface IControlDetailContract {
    interface View {

        MyApplication getMyApplication();
    }

    interface  Presenter {

        String getControlName();

        String getCotrolDesc();

        List<Riesgo> getControlRisks();

        void init();

        Boolean isControlAdded();

        void onAddControlClicked();
    }
}
