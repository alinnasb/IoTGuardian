package es.unican.iotguardian.activities.controles.detail;

import java.util.List;

import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.model.Perfil;
import es.unican.iotguardian.model.Riesgo;
import es.unican.iotguardian.repository.db.ControlDao;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.PerfilDao;

public class ControlDetailPresenter implements IControlDetailContract.Presenter {

    private final IControlDetailContract.View view;
    private ControlDao controlDao;
    private PerfilDao perfilDao;
    private Control control;
    private Perfil perfil;

    public ControlDetailPresenter(Control control, IControlDetailContract.View view) {
        this.control = control;
        this.view = view;
    }

    @Override
    public void init() {
        DaoSession daoSession = view.getMyApplication().getDaoSession();
        controlDao = daoSession.getControlDao();
        perfilDao = daoSession.getPerfilDao();
        perfil = Perfil.getInstance(perfilDao);
    }

    @Override
    public String getControlName() {
        return control.getNombre();
    }

    @Override
    public String getCotrolDesc() {
        return control.getDescripcion();
    }

    @Override
    public List<Riesgo> getControlRisks() {
        return control.getMitigaRiesgos();
    }

    @Override
    public Boolean isControlAdded() {
        Perfil p = perfilDao.load(perfil.getId());
        return p.getControlesAnhadidas().contains(control);
    }

    @Override
    public void onAddControlClicked() {
        if (!isControlAdded()) {
            control.setFk_perfil(perfil.getId());
            perfil.getControlesAnhadidas().add(control);
            controlDao.update(control);
            perfilDao.update(perfil);
        } else {
            control.setFk_perfil(null);
            perfil.getControlesAnhadidas().remove(control);
            controlDao.update(control);
            perfilDao.update(perfil);
        }
    }
}
