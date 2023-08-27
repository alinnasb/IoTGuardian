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

import java.util.List;

import es.unican.iotguardian.activities.controles.detail.ControlDetailView;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.R;

public class RVControlesAdapter extends RecyclerView.Adapter<RVControlesAdapter.ControlViewHolder> {

    private Context context;
    private final List<Control> controles;
    private final List<Control> perfilControls;
    private final LayoutInflater inflater;

    public RVControlesAdapter(Context context, List<Control> controles, List<Control> perfilControls) {
        this.controles = controles;
        this.context = context;
        this.perfilControls = perfilControls;
        this.inflater = LayoutInflater.from(context);
    }

    public RVControlesAdapter(Context context, List<Control> controles) {
        this.controles = controles;
        this.context = context;
        perfilControls = null;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ControlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_controles_control, parent, false);
        return new ControlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ControlViewHolder holder, int position) {
        Control control = controles.get(position);
        holder.control = control;
        holder.controlName_tv.setText(control.getNombre());
        int iconoId = context.getResources().getIdentifier(
                "control" + control.getIdControl(),
                "drawable",
                context.getPackageName());
        if (iconoId != 0) {
            holder.controlIcon_iv.setImageResource(iconoId);
        }

        if (perfilControls != null && perfilControls.contains(control)) {
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
        private final ImageView controlAddedIcon_iv;
        private Control control;

        public ControlViewHolder(@NonNull View itemView) {
            super(itemView);
            controlName_tv = itemView.findViewById(R.id.controlName_tv);
            controlIcon_iv = itemView.findViewById(R.id.controlIcon_iv);
            controlAddedIcon_iv = itemView.findViewById(R.id.controlAddedIcon_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentById(R.id.container))
                    .add(R.id.container, ControlDetailView.newInstance(control))
                    .setReorderingAllowed(true)
                    .addToBackStack("controles")
                    .commit();
        }
    }
}
