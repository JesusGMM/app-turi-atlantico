package app.turiatlantico.recycler;




import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.turiatlantico.R;
import app.turiatlantico.pojos.Evento;

public class EventosRecyclerAdapter extends RecyclerView.Adapter<EventosRecyclerAdapter.EventosHolder>
        implements View.OnClickListener {

    ArrayList<Evento>listEventos;
    View.OnClickListener listener;

    public EventosRecyclerAdapter(ArrayList<Evento> listEventos) {
        this.listEventos = listEventos;
    }

    @NonNull
    @Override
    public EventosRecyclerAdapter.EventosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_evento,null,false);
        view.setOnClickListener(this);
        return new EventosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosRecyclerAdapter.EventosHolder EventosHolder, int i) {

        EventosHolder.tvEvento.setText(listEventos.get(i).getEvento());
        EventosHolder.tvMes.setText(listEventos.get(i).getMes());
        EventosHolder.tvMunicipio.setText(listEventos.get(i).getMunicipio());

    }

    @Override
    public int getItemCount() {
        return listEventos.size();
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
        TextView tvEvento,tvMes,tvMunicipio;
        public EventosHolder(@NonNull View itemView) {
            super(itemView);
            tvEvento=(TextView)itemView.findViewById(R.id.tvEvento);
            tvMes =(TextView)itemView.findViewById(R.id.tvMes);
            tvMunicipio=itemView.findViewById(R.id.tvMunicipio);
        }
    }
}

