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

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.common.adapters.RVDevicesPerfilAdapter;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.R;

public class TabDevicesView extends Fragment {
    private TabDevicesPresenter presenter;
    private RecyclerView apps_rv;
    private RVDevicesPerfilAdapter dvicesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TabDevicesPresenter(this);
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_perfil_apps, container, false);
        apps_rv = layout.findViewById(R.id.appsUsadas_rv);

        apps_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        dvicesAdapter = new RVDevicesPerfilAdapter(getContext(), presenter.getAddedDevices());
        apps_rv.setAdapter(dvicesAdapter);

        DividerItemDecoration dividerA = new DividerItemDecoration(
                apps_rv.getContext(),
                DividerItemDecoration.VERTICAL);
        apps_rv.addItemDecoration(dividerA);
        return layout;
    }

    public void updateAppList(List<DispositivoIoT> appsAnhadidas) {
        dvicesAdapter.updateAppList(appsAnhadidas);
    }

    public MyApplication getMyApplication() {
        return (MyApplication) requireActivity().getApplication();
    }
}


