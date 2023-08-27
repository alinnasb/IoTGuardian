package es.unican.iotguardian.activities.dispositivos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Categoria;
import es.unican.iotguardian.R;

/**
 * Crea y actualiza las vistas que representan cada fila de la lista de categorias.
 */
public class RVCategoriaAdapter extends RecyclerView.Adapter<RVCategoriaAdapter.CategoriaViewHolder> {
    private Context context;
    private final List<Categoria> categorias;
    private final List<DispositivoIoT> perfilApps;
    private final LayoutInflater inflater;

    public RVCategoriaAdapter(Context context, List<Categoria> categorias, List<DispositivoIoT> perfilApps) {
        this.categorias = categorias;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.perfilApps = perfilApps;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_apps_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    @Override
    public void onBindViewHolder(CategoriaViewHolder holder, int position) {
        holder.rvAplicaciones.setAdapter(new RVCategoriaDevicesAdapter(context, categorias.get(position).getAppsCat(), perfilApps));
        holder.rvAplicaciones.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvAplicaciones.setHasFixedSize(true);
        holder.categoriaName_tv.setText(categorias.get(position).getNombre());
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView categoriaName_tv;
        private final RecyclerView rvAplicaciones;

        public CategoriaViewHolder(View itemView) {
            super(itemView);
            categoriaName_tv = itemView.findViewById(R.id.categoriaName_tv);
            rvAplicaciones = itemView.findViewById(R.id.apps_rv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), categoriaName_tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
