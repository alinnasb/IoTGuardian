package es.unican.iotguardian.activities.perfil.tabs;

import java.util.List;

import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Perfil;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.PerfilDao;

public class TabDevicesPresenter {
    private final TabDevicesView view;
    private PerfilDao perfilDao;
    private Perfil perfil;

    public TabDevicesPresenter(TabDevicesView view) {
        this.view = view;
    }

    public void init() {
        DaoSession daoSession = view.getMyApplication().getDaoSession();
        perfilDao = daoSession.getPerfilDao();
        perfil = Perfil.getInstance(perfilDao);
    }

    public List<DispositivoIoT> getAddedDevices() {
        return perfil.getAppsAnhadidas();
    }
}
