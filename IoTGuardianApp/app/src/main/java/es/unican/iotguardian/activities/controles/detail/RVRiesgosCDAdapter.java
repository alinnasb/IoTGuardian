package es.unican.iotguardian.activities.controles.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unican.iotguardian.model.Riesgo;
import es.unican.iotguardian.R;


public class RVRiesgosCDAdapter extends RecyclerView.Adapter<RVRiesgosCDAdapter.RiesgoViewHolder> {

    private Context context;
    private final List<Riesgo> riesgos;
    private final LayoutInflater inflater;

    public RVRiesgosCDAdapter(Context context, List<Riesgo> riesgos) {
        this.context = context;
        this.riesgos = riesgos;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RiesgoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_controles_riesgo, parent, false);
        return new RiesgoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiesgoViewHolder holder, int position) {
        Riesgo riesgo = riesgos.get(position);
        holder.riesgoName_tv.setText(riesgo.getNombre());
        int iconoId = context.getResources().getIdentifier(
                "risk" + riesgo.getIdRiesgo(),
                "drawable",
                context.getPackageName()
        );
        if (iconoId != 0) {
            holder.riesgoIcon_iv.setImageResource(iconoId);
        }

        boolean isExpanded = riesgo.isExpanded();
        holder.riesgoDescription_tv.setText(riesgo.getDescripcion());
        holder.riesgoDescription_tv.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        int arrowIcon = isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down;
        holder.arrow_iv.setImageResource(arrowIcon);
    }

    @Override
    public int getItemCount() {
        return riesgos.size();
    }

    public class RiesgoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView riesgoName_tv;
        private final ImageView riesgoIcon_iv;
        private final TextView riesgoDescription_tv;
        private final ImageView arrow_iv;

        public RiesgoViewHolder(@NonNull View itemView) {
            super(itemView);
            riesgoName_tv = itemView.findViewById(R.id.riesgoName_tv);
            riesgoIcon_iv = itemView.findViewById(R.id.riesgoIcon_iv);
            riesgoDescription_tv = itemView.findViewById(R.id.riesgoDescription_tv);
            arrow_iv = itemView.findViewById(R.id.arrow_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Riesgo riesgo = riesgos.get(position);
                riesgo.setExpanded(!riesgo.isExpanded());
                notifyItemChanged(position);
            }
        }
    }
}
