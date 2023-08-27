package es.unican.iotguardian.activities.dispositivos.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unican.iotguardian.activities.dispositivos.detail.DispositivoDetailView;
import es.unican.iotguardian.activities.main.MainView;
import es.unican.iotguardian.common.adapters.RVDevicesPerfilAdapter;
import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.R;


public class SearchResultView extends Fragment implements ISearchResultContract.View, MainView.RefreshableFragment {

    public static final String QUERY = "query";
    private ISearchResultContract.Presenter presenter;
    private RecyclerView apps_rv;

    public static SearchResultView newInstance(String query) {
        SearchResultView fragment = new SearchResultView();
        Bundle args = new Bundle();
        args.putString(QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchResultPresenter(this);
        presenter.init();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        apps_rv = layout.findViewById(R.id.appsResultSearch_rv);

        apps_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle args = getArguments();
        apps_rv.setAdapter(new RVDevicesPerfilAdapter(getContext(), presenter.doSearch(args.getString(QUERY)), presenter.getPerfilApps()));

        DividerItemDecoration dividerA = new DividerItemDecoration(
                apps_rv.getContext(),
                DividerItemDecoration.VERTICAL);
        apps_rv.addItemDecoration(dividerA);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtén el intent y verifica la acción
        Intent intent = requireActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            presenter.doSearch(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // Maneja el clic en una sugerencia (porque todas las sugerencias usan ACTION_VIEW)
            Uri data = intent.getData();
            showResult(data);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                requireActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) requireActivity().getApplication();
    }

    @Override
    public void refreshData() {

    }

    private void showResult(Uri data) {
        if (data != null) {
            String intentData = data.toString();
            if (intentData.startsWith("app://")) {
                String appName = intentData.substring(6);
                DispositivoIoT dispositivoIoT = presenter.getAppByName(appName);
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
                }
            }
        }
    }
}

