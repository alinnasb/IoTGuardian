package es.unican.iotguardian.activities.dispositivos;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unican.iotguardian.activities.dispositivos.detail.DispositivoDetailView;
import es.unican.iotguardian.activities.dispositivos.search.SearchResultView;
import es.unican.iotguardian.activities.main.MainView;
import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.R;

public class DispositivosView extends Fragment implements IDispositivosContract.View, MainView.RefreshableFragment {

    private IDispositivosContract.Presenter presenter;
    private RecyclerView categorias_rv;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DispositivosPresenter(this);
        presenter.init();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layoutApps = inflater.inflate(R.layout.fragment_apps, container, false);
        categorias_rv = layoutApps.findViewById(R.id.categorias_rv);
        categorias_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        categorias_rv.setAdapter(new RVCategoriaAdapter(getContext(),
                presenter.getCategorias(), presenter.getPerfilDevices()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                categorias_rv.getContext(),
                DividerItemDecoration.VERTICAL);
        categorias_rv.addItemDecoration(dividerItemDecoration);

        return layoutApps;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtén el intent y verifica la acción
        Intent intent = requireActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            showSearchResultView(query);

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // Maneja el clic en una sugerencia (porque todas las sugerencias usan ACTION_VIEW)
            Uri data = intent.getData();
            showAppDetailViewSuggestion(data);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showSearchResultView(String query) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SearchResultView.newInstance(query))
                .setReorderingAllowed(true)
                .addToBackStack("apps")
                .commit();
        // Eliminar los intent anteriores
        requireActivity().setIntent(new Intent());
    }

    private void showAppDetailViewSuggestion(Uri data) {
        if (data != null) {
            String intentData = data.toString();
            if (intentData.startsWith("app://")) {
                String appName = intentData.substring(6);
                DispositivoIoT dispositivoIoT = presenter.getDeviceByName(appName);
                if (dispositivoIoT != null) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, DispositivoDetailView.newInstance(dispositivoIoT))
                            .setReorderingAllowed(true)
                            .addToBackStack("apps")
                            .commit();

                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View rootView = requireView();
                    if (rootView != null) {
                        inputMethodManager.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                    }

                    // Eliminar los intent anteriores
                    requireActivity().setIntent(new Intent());
                }
            }
        }
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) requireActivity().getApplication();
    }

    @Override
    public void showLoadError() {
        String text = getResources().getString(R.string.loadError);
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadCorrect(int appsCount) {
        String text = getResources().getString(R.string.loadCorrect);
        Toast.makeText(this.getContext(), String.format(text, appsCount), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshData() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Eliminar los intent anteriores
        requireActivity().setIntent(new Intent());

        fragmentManager.beginTransaction()
                .replace(R.id.container, new DispositivosView())
                .commit();
    }

}
