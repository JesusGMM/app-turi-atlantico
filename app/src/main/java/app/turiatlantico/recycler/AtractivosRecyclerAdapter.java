package app.turiatlantico.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.turiatlantico.R;
import app.turiatlantico.pojos.Atractivo;
import app.turiatlantico.pojos.Evento;

public class AtractivosRecyclerAdapter extends RecyclerView.Adapter<AtractivosRecyclerAdapter.AtractivosHolder>
implements View.OnClickListener {
    ArrayList<Atractivo> listAtractivos;
    View.OnClickListener listener;

    public AtractivosRecyclerAdapter(ArrayList<Atractivo> listAtractivos) {
        this.listAtractivos = listAtractivos;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (this.listener!=null){
            listener.onClick(v);
        }

    }
    @NonNull
    @Override
    public AtractivosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_atractivo,null,false);
        view.setOnClickListener(this);
        return new AtractivosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtractivosHolder holder, int i) {
        holder.tvNombreMunicipio.setText(listAtractivos.get(i).getNombremunicipio());
        holder.tvNombreSitio.setText(listAtractivos.get(i).getNombresitio());
        holder.tvTipo.setText(listAtractivos.get(i).getTipo());
    }

    @Override
    public int getItemCount() {
        return listAtractivos.size();
    }

    public class AtractivosHolder extends RecyclerView.ViewHolder {
        TextView tvNombreSitio,tvNombreMunicipio,tvTipo;
        public AtractivosHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreSitio=(TextView)itemView.findViewById(R.id.tvNombreSitio);
            tvNombreMunicipio =(TextView)itemView.findViewById(R.id.tvNombreMunicipio);
            tvTipo=itemView.findViewById(R.id.tvTipo);
        }
    }
}
