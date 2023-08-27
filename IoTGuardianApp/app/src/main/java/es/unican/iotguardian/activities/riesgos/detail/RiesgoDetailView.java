package es.unican.iotguardian.activities.riesgos.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unican.iotguardian.activities.main.MainView;
import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.Riesgo;
import es.unican.iotguardian.R;

public class RiesgoDetailView extends Fragment implements IRiesgoDetailContract.View, MainView.RefreshableFragment {

    public static final String FRAGMENT_RIESGO = "riesgo";
    private IRiesgoDetailContract.Presenter presenter;

    public static RiesgoDetailView newInstance(Riesgo riesgo) {
        RiesgoDetailView fragment = new RiesgoDetailView();
        Bundle args = new Bundle();
        args.putParcelable(FRAGMENT_RIESGO, riesgo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frament_riesgo_detail, container, false);

        Bundle args = getArguments();
        if (args != null) {
            // Obtener el riesgo del fragmento que lanzo este fragmento
            Riesgo riesgo = args.getParcelable(FRAGMENT_RIESGO);
            presenter = new RiesgoDetailPresenter(riesgo, this);
            presenter.init();

            // Link a los elementos de la vista
            TextView riesgoName_tv = layout.findViewById(R.id.riesgoDetailName_tv);
            TextView riesgoDesc_tv = layout.findViewById(R.id.riesgoDetailDesc_tv);
            RecyclerView riesgoDetail_controles_rv = layout.findViewById(R.id.riesgoDetail_controles_rv);

            // Asignar valores
            riesgoName_tv.setText(presenter.getRiesgoName());
            riesgoDesc_tv.setText(presenter.getRiesgoDesc());

            riesgoDetail_controles_rv.setLayoutManager(new LinearLayoutManager(getContext()));
            riesgoDetail_controles_rv.setAdapter(new RVControlesRDAdapter(getContext(), presenter.getRiskControls(), presenter.getPerfilControls()));

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(riesgoDetail_controles_rv.getContext(),
                    DividerItemDecoration.VERTICAL);
            riesgoDetail_controles_rv.addItemDecoration(dividerItemDecoration);
        }

        return layout;
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
        // No hay nada que actualizar
    }
}
