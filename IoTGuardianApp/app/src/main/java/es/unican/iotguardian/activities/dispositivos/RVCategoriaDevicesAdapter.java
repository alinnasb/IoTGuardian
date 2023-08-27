package es.unican.iotguardian.activities.dispositivos;

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

public class RVCategoriaDevicesAdapter extends RecyclerView.Adapter<RVCategoriaDevicesAdapter.CategoriaAppViewHolder> {
    private Context context;
    private final List<DispositivoIoT> dispositivoIoTS;
    private final List<DispositivoIoT> perfilDevices;
    private final LayoutInflater inflater;

    public RVCategoriaDevicesAdapter(Context context, List<DispositivoIoT> data, List<DispositivoIoT> perfilDevices) {
        this.context = context;
        this.dispositivoIoTS = data;
        this.perfilDevices = perfilDevices;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoriaAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_apps_app, parent, false);
        return new CategoriaAppViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dispositivoIoTS.size();
    }

    @Override
    public void onBindViewHolder(CategoriaAppViewHolder holder, int position) {
        DispositivoIoT dispositivoIoT = dispositivoIoTS.get(position);
        holder.dispositivoIoT = dispositivoIoT;
        holder.appName_tv.setText(dispositivoIoT.getNombre());
        Picasso.get().load(dispositivoIoT.getIcono())
                .resize(600, 600)
                .centerCrop()
                .into(holder.appIcon_iv);

        if (perfilDevices.contains(dispositivoIoT)) {
            holder.appAddedIcon_iv.setVisibility(View.VISIBLE);
            holder.appAddedInfo_tv.setVisibility(View.VISIBLE);
        } else {
            holder.appAddedIcon_iv.setVisibility(View.GONE);
            holder.appAddedInfo_tv.setVisibility(View.GONE);
        }
    }

    public class CategoriaAppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView appIcon_iv;
        private final TextView appName_tv;
        private final ImageView appAddedIcon_iv;
        private final TextView appAddedInfo_tv;
        private DispositivoIoT dispositivoIoT;

        public CategoriaAppViewHolder(View itemView) {
            super(itemView);
            appName_tv = itemView.findViewById(R.id.appName_tv);
            appIcon_iv = itemView.findViewById(R.id.appIcon_iv);
            appAddedIcon_iv = itemView.findViewById(R.id.appAddedIcon_iv);
            appAddedInfo_tv = itemView.findViewById(R.id.appAddedInfo_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            AppCompatActivity activity = (AppCompatActivity) context;
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, DispositivoDetailView.newInstance(dispositivoIoT))
                    .setReorderingAllowed(true)
                    .addToBackStack("apps")
                    .commit();
        }
    }
}
