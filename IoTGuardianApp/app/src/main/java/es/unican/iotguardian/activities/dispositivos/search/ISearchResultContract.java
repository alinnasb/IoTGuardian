package es.unican.iotguardian.activities.dispositivos.search;

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.DispositivoIoT;

public interface ISearchResultContract {

    interface View {
        MyApplication getMyApplication();
    }

    interface Presenter {

        void init();

        List<DispositivoIoT> doSearch(String query);

        DispositivoIoT getAppByName(String appName);

        List<DispositivoIoT> getPerfilApps();
    }
}
