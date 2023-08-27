package es.unican.iotguardian.activities.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import es.unican.iotguardian.activities.main.MainView;
import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.R;

public class PerfilView extends Fragment implements IPerfilContract.View, MainView.RefreshableFragment {
    private IPerfilContract.Presenter presenter;
    private ViewPager2 viewPager;
    private CardView nivelRiesgo_cv;
    private TextView nivelRiesgo_tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PerfilPresenter(this);
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_perfil, container, false);
        viewPager = layout.findViewById(R.id.viewpager);
        nivelRiesgo_cv = layout.findViewById(R.id.nivelRiesgo_cv);
        nivelRiesgo_tv = layout.findViewById(R.id.nivelRiesgo_tv);

        viewPager.setAdapter(new VPPerfilAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), getLifecycle()));

        TabLayout tabLayout = layout.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(VPPerfilAdapter.Tab.byPosition(position).getTitle())
                ).attach();

        setNivelRiesgo(presenter.getNivelRiesgo());

        return layout;
    }

    private void setNivelRiesgo(int nivelRiesgo) {
        if (nivelRiesgo >= 70) {
            nivelRiesgo_tv.setText(R.string.nivelRiesgoAlto);
            nivelRiesgo_cv.setCardBackgroundColor(getResources().getColor(R.color.riesgoAlto));
        } else if (nivelRiesgo >= 30) {
            nivelRiesgo_tv.setText(R.string.nivelRiesgoMedio);
            nivelRiesgo_cv.setCardBackgroundColor(getResources().getColor(R.color.riesgoMedio));
        } else {
            nivelRiesgo_tv.setText(R.string.nivelRiesgoBajo);
            nivelRiesgo_cv.setCardBackgroundColor(getResources().getColor(R.color.riesgoBajo));
        }
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) requireActivity().getApplication();
    }

    @Override
    public void refreshData() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new PerfilView())
                .commit();
    }
}
