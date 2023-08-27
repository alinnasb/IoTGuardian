package es.unican.iotguardian.activities.perfil.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.common.adapters.RVControlesAdapter;
import es.unican.iotguardian.R;

public class TabControlesView extends Fragment {
    private TabControlesPresenter presenter;
    private RecyclerView controles_rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TabControlesPresenter(this);
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_perfil_controles, container, false);
        controles_rv = layout.findViewById(R.id.controlesAplicados_rv);

        controles_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        controles_rv.setAdapter(new RVControlesAdapter(getContext(), presenter.getControlesAnhadidos()));

        DividerItemDecoration dividerC = new DividerItemDecoration(
                controles_rv.getContext(),
                DividerItemDecoration.VERTICAL);
        controles_rv.addItemDecoration(dividerC);
        return layout;
    }

    public MyApplication getMyApplication() {
        return (MyApplication) requireActivity().getApplication();
    }
}
