package es.unican.iotguardian.activities.main;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.unican.iotguardian.R;

public class MainView extends AppCompatActivity implements IMainContract.View {

    private static final String SELECTION = "SELECTION";
    private BottomNavigationView bottomNavigationView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private IMainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        setupToolbar();
        setupBottomMenu(savedInstanceState);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
                    if (fragment instanceof RefreshableFragment) {
                        ((RefreshableFragment) fragment).refreshData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        );
    }

    /**
     * Conserva la seleccion del menu actual para cuando se rote la pantalla.
     * @param outState guarda el identificador del item del menú seleccionado
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTION, bottomNavigationView.getSelectedItemId());
    }

    /**
     * Establece una Toolbar como ActionBar de la activity.
     * Muestra la flecha de retroceso si hay fragmentos en la pila de retroceso.
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backStackEntryCount == 0) {
                    // No hay fragmentos en la pila de retroceso, ocultar la flecha de retroceso
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    }
                } else {
                    // Hay fragmentos en la pila de retroceso, mostrar la flecha de retroceso
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                }
            }
        });
    }

    /**
     * Vincula el menu de navegacion inferior con su listener y establece la pagina inicial.
     * @param savedInstanceState
     */
    private void setupBottomMenu(Bundle savedInstanceState) {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::onItemSelectedListener);

        // Mostrar fragment inicial al cargar la pantalla
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.page_apps);
        } else {
            bottomNavigationView.setSelectedItemId(savedInstanceState.getInt(SELECTION));
        }
    }

    /**
     * Listener para el menu de navegacion inferior.
     * @param item Elemento pulsado
     * @return true si ha ido correcto
     *          IllegalArgumentException si ha ocurrido un error
     */
    private boolean onItemSelectedListener(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.page_apps:
                resetToolbar();
                presenter.onNavAppsClicked();
                return true;
            case R.id.page_riesgos:
                resetToolbar();
                presenter.onNavRiesgosClicked();
                return true;
            case R.id.page_controles:
                resetToolbar();
                presenter.onNavControlesClicked();
                return true;
            case R.id.page_perfil:
                resetToolbar();
                presenter.onNavPerfilClicked();
                return true;
            default:
                throw new IllegalArgumentException("Item not implemented: " + item.getItemId());
        }
    }

    private void resetToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        invalidateOptionsMenu();
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    @Override
    public void showFragment(Fragment frg, @StringRes int title) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.bottom_nav_enter, R.anim.bottom_nav_exit)
                .replace(R.id.container, frg)
                .setReorderingAllowed(true)
                .commit();
        setTitle(title); // Visualizar el titulo de la pagina en la toolbar
    }

    public interface RefreshableFragment {
        void refreshData();
    }
}