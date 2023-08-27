package es.unican.iotguardian.activities.riesgos.detail;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.List;

import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.model.Perfil;
import es.unican.iotguardian.model.Riesgo;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.PerfilDao;

public class RiesgoDetailPresenter implements  IRiesgoDetailContract.Presenter {

    private IRiesgoDetailContract.View view;
    private Riesgo riesgo;
    private PerfilDao perfilDao;

    public RiesgoDetailPresenter(Riesgo riesgo, IRiesgoDetailContract.View view) {
        this.riesgo = riesgo;
        this.view = view;
    }

    @Override
    public void init() {
        DaoSession daoSession = view.getMyApplication().getDaoSession();
        perfilDao = daoSession.getPerfilDao();
    }

    @Override
    public String getRiesgoName() {
        return riesgo.getNombre();
    }

    @Override
    public String getRiesgoDesc() {
        return riesgo.getDescripcion();
    }

    @Override
    public List<Control> getRiskControls() {
        return riesgo.getControles();
    }

    @Override
    public List<Control> getPerfilControls() {
        Perfil perfil = Perfil.getInstance(perfilDao);
        return perfil.getControlesAnhadidas();
    }
}
