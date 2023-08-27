package es.unican.iotguardian.repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import es.unican.iotguardian.common.Callback;
import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.common.prefs.Prefs;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Categoria;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.model.JoinCategoriasWithRiesgos;
import es.unican.iotguardian.model.JoinRiesgosWithControles;
import es.unican.iotguardian.model.Riesgo;
import es.unican.iotguardian.repository.db.CategoriaDao;
import es.unican.iotguardian.repository.db.ControlDao;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.DispositivoIoTDao;
import es.unican.iotguardian.repository.db.JoinCategoriasWithRiesgosDao;
import es.unican.iotguardian.repository.db.JoinRiesgosWithControlesDao;
import es.unican.iotguardian.repository.db.RiesgoDao;
import es.unican.iotguardian.repository.rest.IoTGuardianService;

/**
 * Implementacion de un repositorio de los recursos de AppWiseService.
 * El repositorio tambien persiste en una base de datos local las listas de
 * aplicaciones, riesgos y controles recuperados.
 */
public class IoTGuardianRepository implements IIoTGuardinRepository {

    private static final String KEY_LAST_SAVED_A = "KEY_LAST_SAVED_A";
    private static final String KEY_LAST_SAVED_R = "KEY_LAST_SAVED_R";
    private static final String KEY_LAST_SAVED_C = "KEY_LAST_SAVED_C";
    private static final String KEY_LAST_SAVED_CA = "KEY_LAST_SAVED_CA";

    private final MyApplication application;
    private final DaoSession daoSession;

    public IoTGuardianRepository(MyApplication application) {
        this.application = application;
        this.daoSession = application.getDaoSession();
    }

    @Override
    public void requestDispositivos(Callback<DispositivoIoT[]> cb, String categoria) {
        IoTGuardianService.requestDispositivos(new Callback<DispositivoIoT[]>() {
            @Override
            public void onSuccess(DispositivoIoT[] data) {
                persistToDBDispositivos(data);
                cb.onSuccess(data);
            }

            @Override
            public void onFailure() {
                cb.onFailure();
            }
        }, categoria);
    }

    @Override
    public DispositivoIoT[] getDispositivos(String categoria) {
        DispositivoIoT[] response = IoTGuardianService.getDispositivos(categoria);
        persistToDBDispositivos(response);
        return response;
    }

    private void persistToDBDispositivos(DispositivoIoT[] dispositivos) {
        if (dispositivos != null) {
            DispositivoIoTDao dispositivoIoTDao = daoSession.getDispositivoIoTDao();
            CategoriaDao categoriaDao = daoSession.getCategoriaDao();
            for (DispositivoIoT a : dispositivos) {
                DispositivoIoT aBD = dispositivoIoTDao.load(a.getIdDispositivo());
                if (aBD == null) {
                    Categoria cat = categoriaDao.load(a.getCat().getIdCategoria());
                    a.setFk_categoria(cat.getIdCategoria());
                    dispositivoIoTDao.insert(a);
                } else {
                    Categoria cat = categoriaDao.load(a.getCat().getIdCategoria());
                    a.setFk_categoria(cat.getIdCategoria());
                    Long fk_perfil = aBD.getFk_perfil();
                    a.setFk_perfil(fk_perfil);
                    dispositivoIoTDao.update(a);
                }
            }
            //Prefs.from(application).putInstant(KEY_LAST_SAVED_A, Instant.now());
        }
    }

    @Override
    public void requestDispositivo(Callback<DispositivoIoT> cb, String nombre) {
        IoTGuardianService.requestDispositivo(new Callback<DispositivoIoT>() {
            @Override
            public void onSuccess(DispositivoIoT data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure() {
                cb.onFailure();
            }
        }, nombre);
    }

    @Override
    public DispositivoIoT getDispositivo(String nombre) {
        return IoTGuardianService.getDispositivo(nombre);
    }

    @Override
    public void requestRiesgos(Callback<Riesgo[]> cb) {
        IoTGuardianService.requestRiesgos(new Callback<Riesgo[]>() {
            @Override
            public void onSuccess(Riesgo[] data) {
                persistToDBRiesgos(data);
                cb.onSuccess(data);
            }

            @Override
            public void onFailure() {
                cb.onFailure();
            }
        });
    }

    @Override
    public Riesgo[] getRiesgos() {
        Riesgo[] response = IoTGuardianService.getRiesgos();
        persistToDBRiesgos(response);
        return response;
    }

    private void persistToDBRiesgos(Riesgo[] riesgos) {
        if (riesgos != null) {
            RiesgoDao riesgoDao = daoSession.getRiesgoDao();
            JoinRiesgosWithControlesDao rcDao = daoSession.getJoinRiesgosWithControlesDao();
            for (Riesgo r : riesgos) {
                if (riesgoDao.load(r.getIdRiesgo()) == null) {
                    List<Control> controles = r.getControles();
                    riesgoDao.insert(r);
                    for (Control c : controles) {
                        JoinRiesgosWithControles rc = new JoinRiesgosWithControles();
                        rc.setIdRiesgo(r.getIdRiesgo());
                        rc.setIdControl(c.getIdControl());
                        rcDao.insert(rc);
                    }
                }
            }
            //Prefs.from(application).putInstant(KEY_LAST_SAVED_R, Instant.now());
        }
    }

    @Override
    public void requestRiesgo(Callback<Riesgo> cb, Long id) {
        IoTGuardianService.requestRiesgo(new Callback<Riesgo>() {
            @Override
            public void onSuccess(Riesgo data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure() {
                cb.onFailure();
            }
        }, id);
    }

    @Override
    public Riesgo getRiesgo(Long id) {
        return IoTGuardianService.getRiesgo(id);
    }

    @Override
    public void requestControles(Callback<Control[]> cb) {
        IoTGuardianService.requestControles(new Callback<Control[]>() {
            @Override
            public void onSuccess(Control[] data) {
                persistToDBControles(data);
                cb.onSuccess(data);
            }

            @Override
            public void onFailure() {
                cb.onFailure();
            }
        });
    }

    @Override
    public Control[] getControles() {
        Control[] response = IoTGuardianService.getControles();
        persistToDBControles(response);
        return response;
    }

    private void persistToDBControles(Control[] controles) {
        if (controles != null) {
            ControlDao controlDao = daoSession.getControlDao();
            for (Control c : controles) {
                if (controlDao.load(c.getIdControl()) == null) {
                    controlDao.insert(c);
                }
            }
            //Prefs.from(application).putInstant(KEY_LAST_SAVED_C, Instant.now());
        }
    }

    @Override
    public void requestControl(Callback<Control> cb, Long id) {
        IoTGuardianService.requestControl(new Callback<Control>() {
            @Override
            public void onSuccess(Control data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFailure() {
                cb.onFailure();
            }
        }, id);
    }

    @Override
    public Control getControl(Long id) {
        return IoTGuardianService.getControl(id);
    }

    @Override
    public void requestCategorias(Callback<Categoria[]> cb) {
        IoTGuardianService.requestCategorias(new Callback<Categoria[]>() {
            @Override
            public void onSuccess(Categoria[] data) {
                persistToDBCategorias(data);
                cb.onSuccess(data);
            }

            @Override
            public void onFailure() {
                cb.onFailure();
            }
        });
    }

    @Override
    public Categoria[] getCategorias() {
        Categoria[] response = IoTGuardianService.getCategorias();
        persistToDBCategorias(response);
        return response;
    }

    private void persistToDBCategorias(Categoria[] categorias) {
        if (categorias != null) {
            CategoriaDao categoriaDao = daoSession.getCategoriaDao();
            JoinCategoriasWithRiesgosDao crDao = daoSession.getJoinCategoriasWithRiesgosDao();
            for (Categoria c : categorias) {
                if (categoriaDao.load(c.getIdCategoria()) == null) {
                    List<Riesgo> riesgos = c.getRiesgos();
                    categoriaDao.insert(c);
                    for (Riesgo r : riesgos) {
                        JoinCategoriasWithRiesgos cr = new JoinCategoriasWithRiesgos();
                        cr.setIdCategoria(c.getIdCategoria());
                        cr.setIdRiesgo(r.getIdRiesgo());
                        crDao.insert(cr);
                    }
                }
            }
            //Prefs.from(application).putInstant(KEY_LAST_SAVED_CA, Instant.now());
        }
    }

    /**
     * Retorna true si los recursos guardados en la BD son antiguos a la
     * cantidad específicada de minutos.
     * @param minutes
     * @return true si los recursos guardados en la BD son antiguos a la
     * cantidad específicada de minutos
     */
    private boolean lastDownloadOlderThan(int minutes, String resourceName) {
        Instant lastDownloaded = Prefs.from(application).getInstant(resourceName);
        if (lastDownloaded == null) {
            return true;
        } else {
            Instant now = Instant.now();
            long sinceLastDownloaded = ChronoUnit.MINUTES.between(lastDownloaded, now);  // minutes
            return (sinceLastDownloaded > minutes);
        }
    }
}
