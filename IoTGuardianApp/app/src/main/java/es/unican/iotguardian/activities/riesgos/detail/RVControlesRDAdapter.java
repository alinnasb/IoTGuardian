package es.unican.iotguardian.activities.riesgos.detail;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unican.iotguardian.common.MyApplication;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.R;

public class RVControlesRDAdapter extends RecyclerView.Adapter<RVControlesRDAdapter.ControlViewHolder> {

    private Context context;
    private final List<Control> controles;
    private final List<Control> perfilControls;
    private final LayoutInflater inflater;

    public RVControlesRDAdapter(Context context, List<Control> controles, List<Control> perfilControls) {
        this.controles = controles;
        this.context = context;
        this.perfilControls = perfilControls;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ControlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_riesgos_control, parent, false);
        return new ControlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ControlViewHolder holder, int position) {
        Control control = controles.get(position);
        holder.controlName_tv.setText(control.getNombre());
        int iconoId = context.getResources().getIdentifier(
                "control" + control.getIdControl(),
                "drawable",
                context.getPackageName());
        if (iconoId != 0) {
            holder.controlIcon_iv.setImageResource(iconoId);
        }

        boolean isExpanded = control.isExpanded();
        holder.controlDescription_tv.setText(control.getDescripcion());
        holder.controlDescription_tv.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        int arrowIcon = isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down;
        holder.arrow_iv.setImageResource(arrowIcon);

        if (perfilControls.contains(control)) {
            holder.controlAddedIcon_iv.setVisibility(View.VISIBLE);
        } else {
            holder.controlAddedIcon_iv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return controles.size();
    }

    public class ControlViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView controlName_tv;
        private final ImageView controlIcon_iv;
        private final TextView controlDescription_tv;
        private final ImageView controlAddedIcon_iv;
        private final ImageView arrow_iv;

        public ControlViewHolder(@NonNull View itemView) {
            super(itemView);

            controlName_tv = itemView.findViewById(R.id.controlName_tv);
            controlIcon_iv = itemView.findViewById(R.id.controlIcon_iv);
            controlDescription_tv = itemView.findViewById(R.id.controlDescription_tv);
            controlAddedIcon_iv = itemView.findViewById(R.id.controlAddedIcon_iv);
            arrow_iv = itemView.findViewById(R.id.arrow_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Control control = controles.get(position);
                control.setExpanded(!control.isExpanded());
                notifyItemChanged(position);
            }
        }
    }
}
