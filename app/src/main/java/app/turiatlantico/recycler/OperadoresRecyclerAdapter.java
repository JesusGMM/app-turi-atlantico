package app.turiatlantico.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.turiatlantico.R;
import app.turiatlantico.pojos.Operador;

public class OperadoresRecyclerAdapter extends RecyclerView.Adapter<OperadoresRecyclerAdapter.EventosHolder>
        implements View.OnClickListener {

    ArrayList<Operador> listOperadores;
    View.OnClickListener listener;

    public OperadoresRecyclerAdapter(ArrayList<Operador> listOperadores) {
        this.listOperadores = listOperadores;
    }

    @NonNull
    @Override
    public OperadoresRecyclerAdapter.EventosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_operador,null,false);
        view.setOnClickListener(this);
        return new EventosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperadoresRecyclerAdapter.EventosHolder EventosHolder, int i) {

        EventosHolder.tvOperadorTuristico.setText(listOperadores.get(i).getOperador_turistico());
        EventosHolder.tvDireccionComercial.setText(listOperadores.get(i).getDireccion_comercial());
        EventosHolder.tvTelefono.setText(listOperadores.get(i).getTelefono());

    }

    @Override
    public int getItemCount() {
        return listOperadores.size();
    }
    public void setOnclickListener (View.OnClickListener listener){
        this.listener= listener;
    }
    @Override
    public void onClick(View v) {
        if (this.listener!=null){
            listener.onClick(v);
        }

    }

    public class EventosHolder extends RecyclerView.ViewHolder {
        TextView tvOperadorTuristico,tvTelefono,tvDireccionComercial;
        public EventosHolder(@NonNull View itemView) {
            super(itemView);
            tvOperadorTuristico=(TextView)itemView.findViewById(R.id.tvOperadorTuristico);
            tvTelefono =(TextView)itemView.findViewById(R.id.tvTelefono);
            tvDireccionComercial=itemView.findViewById(R.id.tvDireccionComercial);
        }
    }
}