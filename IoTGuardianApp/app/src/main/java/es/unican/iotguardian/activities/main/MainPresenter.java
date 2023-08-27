package es.unican.iotguardian.activities.main;

import es.unican.iotguardian.activities.dispositivos.DispositivosView;
import es.unican.iotguardian.activities.controles.ControlesView;
import es.unican.iotguardian.activities.perfil.PerfilView;
import es.unican.iotguardian.activities.riesgos.RiesgosView;
import es.unican.iotguardian.R;

public class MainPresenter implements IMainContract.Presenter {

    private final IMainContract.View view;

    public MainPresenter(IMainContract.View view) {
        this.view = view;
    }

    @Override
    public void onNavAppsClicked() {
        view.showFragment(new DispositivosView(), R.string.bottom_nav_apps);
    }

    @Override
    public void onNavRiesgosClicked() {
        view.showFragment(new RiesgosView(), R.string.bottom_nav_riesgos);
    }

    @Override
    public void onNavControlesClicked() {
        view.showFragment(new ControlesView(), R.string.bottom_nav_controles);
    }

    @Override
    public void onNavPerfilClicked() {
        view.showFragment(new PerfilView(), R.string.bottom_nav_perfil);
    }

}
