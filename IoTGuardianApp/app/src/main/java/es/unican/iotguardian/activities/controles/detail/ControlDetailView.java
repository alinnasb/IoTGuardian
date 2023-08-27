package es.unican.iotguardian.activities.controles.detail;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unican.iotguardian.activities.main.MainView;
import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.R;

public class ControlDetailView extends Fragment implements IControlDetailContract.View, MainView.RefreshableFragment {

    public static final String FRAGMENT_CONTROL = "control";
    private IControlDetailContract.Presenter presenter;
    private Button controlAdd_bt;

    public static ControlDetailView newInstance(Control control) {
        ControlDetailView fragment = new ControlDetailView();
        Bundle args = new Bundle();
        args.putParcelable(FRAGMENT_CONTROL, control);
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
        View layout = inflater.inflate(R.layout.fragment_control_detail, container, false);

        Bundle args = getArguments();
        if (args != null) {
            // Obtener el control del fragmento que lanzo este fragmento
            Control control = args.getParcelable(FRAGMENT_CONTROL);
            presenter = new ControlDetailPresenter(control, this);
            presenter.init();

            // Link a los elementos de la vista
            TextView controlName_tv = layout.findViewById(R.id.controlDetailName_tv);
            TextView controlDesc_tv = layout.findViewById(R.id.controlDetailDesc_tv);
            RecyclerView controlDetail_riesgos_rv = layout.findViewById(R.id.controlDetail_riesgos_rv);
            controlAdd_bt = layout.findViewById(R.id.controlAdd_bt);

            // Asignar valores
            controlName_tv.setText(presenter.getControlName());
            controlDesc_tv.setText(presenter.getCotrolDesc());

            controlDetail_riesgos_rv.setLayoutManager(new LinearLayoutManager(getContext()));
            controlDetail_riesgos_rv.setAdapter(new RVRiesgosCDAdapter(getContext(), presenter.getControlRisks()));

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(controlDetail_riesgos_rv.getContext(),
                    DividerItemDecoration.VERTICAL);
            controlDetail_riesgos_rv.addItemDecoration(dividerItemDecoration);

            updateControlAddButton(presenter.isControlAdded());
            controlAdd_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onAddControlClicked();
                    updateControlAddButton(presenter.isControlAdded());
                }
            });
        }

        return layout;
    }

    private void updateControlAddButton(Boolean isControlAdded) {
        if (isControlAdded) {
            if (isDarkModeEnabled()) {
                controlAdd_bt.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.dark_background)));
            } else {
                controlAdd_bt.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.light_background)));
            }

            controlAdd_bt.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
            controlAdd_bt.setText(R.string.app_detail_remove);
        } else {
            if (isDarkModeEnabled()) {
                controlAdd_bt.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            } else {
                controlAdd_bt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }
            controlAdd_bt.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.primary)));
            controlAdd_bt.setText(R.string.app_detail_add);
        }
    }

    private boolean isDarkModeEnabled() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
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
