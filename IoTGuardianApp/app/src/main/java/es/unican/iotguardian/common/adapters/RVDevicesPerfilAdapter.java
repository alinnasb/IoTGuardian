package es.unican.iotguardian.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.unican.iotguardian.activities.dispositivos.detail.DispositivoDetailView;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.R;

public class RVDevicesPerfilAdapter extends RecyclerView.Adapter<RVDevicesPerfilAdapter.AppViewHolder> {

    private Context context;
    private final List<DispositivoIoT> apps;
    private final List<DispositivoIoT> perfilApps;
    private final LayoutInflater inflater;

    public RVDevicesPerfilAdapter(Context context, List<DispositivoIoT> apps) {
        this.apps = apps;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.perfilApps = null;
    }

    public RVDevicesPerfilAdapter(Context context, List<DispositivoIoT> apps, List<DispositivoIoT> perfilApps) {
        this.apps = apps;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.perfilApps = perfilApps;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_perfil_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        DispositivoIoT app = apps.get(position);
        holder.dispositivoIoT = app;
        holder.appName_tv.setText(app.getNombre());
        holder.appCategory_tv.setText(app.getCategoria().getNombre());
        Picasso.get().load(app.getIcono()).into(holder.appIcon_iv);

        if (perfilApps != null && perfilApps.contains(app)) {
            holder.infoAddedIcon_iv.setVisibility(View.VISIBLE);
        } else {
            holder.infoAddedIcon_iv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public void updateAppList(List<DispositivoIoT> updatedApps) {
        apps.clear();
        apps.addAll(updatedApps);
        notifyDataSetChanged();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView appName_tv;
        private final ImageView appIcon_iv;
        private final TextView appCategory_tv;
        private final ImageView infoAddedIcon_iv;
        private DispositivoIoT dispositivoIoT;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            appName_tv = itemView.findViewById(R.id.appAddedName_tv);
            appIcon_iv = itemView.findViewById(R.id.appAddedIcon_iv);
            appCategory_tv = itemView.findViewById(R.id.appAddedCategory_tv);
            infoAddedIcon_iv = itemView.findViewById(R.id.infoAddedIcon_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentById(R.id.container))
                    .add(R.id.container, DispositivoDetailView.newInstance(dispositivoIoT))
                    .setReorderingAllowed(true)
                    .addToBackStack("perfil")
                    .commit();
        }
    }
}
