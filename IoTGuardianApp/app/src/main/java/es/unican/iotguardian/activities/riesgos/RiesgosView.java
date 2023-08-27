package es.unican.iotguardian.activities.riesgos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unican.iotguardian.activities.main.MainView;
import es.unican.iotguardian.common.adapters.RVRiesgosAdapter;
import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.R;

public class RiesgosView extends Fragment implements IRiesgosContract.View, MainView.RefreshableFragment {
    private IRiesgosContract.Presenter presenter;
    private RecyclerView riesgos_rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RiesgosPresenter(this);
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_riesgos, container, false);
        riesgos_rv = layout.findViewById(R.id.riesgos_rv);
        riesgos_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        riesgos_rv.setAdapter(new RVRiesgosAdapter(getContext(), presenter.getRiesgos()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                riesgos_rv.getContext(),
                DividerItemDecoration.VERTICAL);
        riesgos_rv.addItemDecoration(dividerItemDecoration);

        return layout;
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) requireActivity().getApplication();
    }

    @Override
    public void refreshData() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new RiesgosView())
                .commit();
    }
}
