package es.unican.iotguardian.activities.dispositivos.detail;

import java.util.List;

import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Perfil;
import es.unican.iotguardian.model.Riesgo;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.DispositivoIoTDao;
import es.unican.iotguardian.repository.db.PerfilDao;

public class DispositivoDetailPresenter implements IDispositivoDetailContract.Presenter {

    private final IDispositivoDetailContract.View view;
    private DispositivoIoTDao dispositivoIoTDao;
    private PerfilDao perfilDao;
    private DispositivoIoT dispositivoIoT;
    private Perfil perfil;

    public DispositivoDetailPresenter(DispositivoIoT dispositivoIoT, IDispositivoDetailContract.View view) {
        this.dispositivoIoT = dispositivoIoT;
        this.view = view;
    }

    @Override
    public void init() {
        DaoSession daoSession = view.getMyApplication().getDaoSession();
        dispositivoIoTDao = daoSession.getDispositivoIoTDao();
        perfilDao = daoSession.getPerfilDao();
        perfil = Perfil.getInstance(perfilDao);
    }

    @Override
    public List<Riesgo> getDeviceRisks() {
        return dispositivoIoT.getCategoria().getRiesgos();
    }

    @Override
    public String getDeviceIcono() {
        return dispositivoIoT.getIcono();
    }

    @Override
    public String getDeviceName() {
        return dispositivoIoT.getNombre();
    }

    @Override
    public String getDeviceCategory() {
        return dispositivoIoT.getCategoria().getNombre();
    }

    @Override
    public void onAddDeviceClicked() {
        if (!isDeviceAdded()) {
            dispositivoIoT.setFk_perfil(perfil.getId());
            perfil.getAppsAnhadidas().add(dispositivoIoT);
            dispositivoIoTDao.update(dispositivoIoT);
            perfilDao.update(perfil);
        } else {
            dispositivoIoT.setFk_perfil(null);
            perfil.getAppsAnhadidas().remove(dispositivoIoT);
            dispositivoIoTDao.update(dispositivoIoT);
            perfilDao.update(perfil);
        }
    }

    @Override
    public boolean isDeviceAdded() {
        return perfil.getAppsAnhadidas().contains(dispositivoIoT);
    }

    @Override
    public List<DispositivoIoT> getPerfilDevices() {
        return perfil.getAppsAnhadidas();
    }
}
