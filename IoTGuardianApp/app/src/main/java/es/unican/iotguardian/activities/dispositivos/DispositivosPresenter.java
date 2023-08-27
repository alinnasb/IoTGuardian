package es.unican.iotguardian.activities.dispositivos;

import android.database.sqlite.SQLiteException;

import java.util.List;

import es.unican.iotguardian.common.Callback;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Categoria;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.model.Perfil;
import es.unican.iotguardian.model.Riesgo;
import es.unican.iotguardian.repository.IoTGuardianRepository;
import es.unican.iotguardian.repository.IIoTGuardinRepository;
import es.unican.iotguardian.repository.db.CategoriaDao;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.DispositivoIoTDao;
import es.unican.iotguardian.repository.db.PerfilDao;

public class DispositivosPresenter implements IDispositivosContract.Presenter {

    private final IDispositivosContract.View view;
    private DispositivoIoTDao dispositivoIoTDao;
    private CategoriaDao categoriaDao;
    private PerfilDao perfilDao;
    private IIoTGuardinRepository repository;

    public DispositivosPresenter(IDispositivosContract.View view) {
        this.view = view;
    }

    public DispositivosPresenter(IDispositivosContract.View view, IIoTGuardinRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void init() {
        DaoSession daoSession = view.getMyApplication().getDaoSession();
        dispositivoIoTDao = daoSession.getDispositivoIoTDao();
        categoriaDao = daoSession.getCategoriaDao();
        perfilDao = daoSession.getPerfilDao();

        if (repository == null) {
            repository = new IoTGuardianRepository(view.getMyApplication());
        }

        doAsyncInit();
    }

    private void doAsyncInit() {
        repository.requestControles(new Callback<Control[]>() {
            @Override
            public void onSuccess(Control[] controles) {
                repository.requestRiesgos(new Callback<Riesgo[]>() {
                    @Override
                    public void onSuccess(Riesgo[] riesgos) {
                        repository.requestCategorias(new Callback<Categoria[]>() {
                            @Override
                            public void onSuccess(Categoria[] categorias) {
                                repository.requestDispositivos(new Callback<DispositivoIoT[]>() {
                                    @Override
                                    public void onSuccess(DispositivoIoT[] aplicaciones) {
                                        view.showLoadCorrect(aplicaciones.length);
                                    }

                                    @Override
                                    public void onFailure() {
                                        view.showLoadError();
                                    }
                                }, null);
                            }
                            @Override
                            public void onFailure() {
                                view.showLoadError();
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        view.showLoadError();
                    }
                });
            }

            @Override
            public void onFailure() {
                view.showLoadError();
            }
        });
    }

    private void doSyncInit() {
        if (repository.getControles() == null ||
                repository.getRiesgos() == null ||
                repository.getCategorias() == null ||
                repository.getDispositivos(null) == null) {
            view.showLoadError();
        } else {
            view.showLoadCorrect((int) dispositivoIoTDao.count());
        }
    }

    @Override
    public List<Categoria> getCategorias() {
        List<Categoria> result = null;
        try {
            result = categoriaDao.loadAll();
        } catch (SQLiteException e) {
            view.showLoadError();
        }
        return result;
    }

    @Override
    public List<DispositivoIoT> getPerfilDevices() {
        List<DispositivoIoT> result = null;
        try {
            Perfil perfil = Perfil.getInstance(perfilDao);
            result = perfil.getAppsAnhadidas();
        } catch (SQLiteException e) {
            view.showLoadError();
        }
        return result;
    }

    @Override
    public DispositivoIoT getDeviceByName(String appName) {
        return dispositivoIoTDao.queryBuilder().where(DispositivoIoTDao.Properties.Nombre.like(appName)).unique();
    }
}
