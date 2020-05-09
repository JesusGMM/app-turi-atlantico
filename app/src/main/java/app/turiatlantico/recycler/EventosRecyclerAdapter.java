package app.turiatlantico.recycler;




import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


import app.turiatlantico.R;
import app.turiatlantico.pojos.Evento;


public class EventosRecyclerAdapter extends RecyclerView.Adapter<EventosRecyclerAdapter.EventosHolder>
        implements View.OnClickListener {

    ArrayList<Evento>listEventos;
    View.OnClickListener listener;
    Context getContext;

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
    public void onBindViewHolder(@NonNull final EventosRecyclerAdapter.EventosHolder EventosHolder, final int i) {
        final StorageReference reference = FirebaseStorage.getInstance().getReference();
        String Inicial = listEventos.get(i).getNombre();
        Inicial = Inicial.substring(0,1);

        reference.child("Iconos/"+Inicial+".png").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext)
                                .load(uri)
                                .centerCrop()
                                .into(EventosHolder.icono);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //image.setImageResource(R.drawable.icon_imagen);
                Toast toast = Toast.makeText(getContext,
                        "Hubo un error al cargar el icono "+ listEventos.get(i).getNombre(),
                        Toast.LENGTH_SHORT);
                toast.show();
                EventosHolder.icono.setImageResource(R.drawable.icon_imagen);
            }
        });

        EventosHolder.tvEvento.setText(listEventos.get(i).getNombre());
        EventosHolder.tvMes.setText(listEventos.get(i).getMes());
        EventosHolder.tvMunicipio.setText(listEventos.get(i).getDirrecion());

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
        ImageView icono;
        public EventosHolder(@NonNull View itemView) {
            super(itemView);
            tvEvento = itemView.findViewById(R.id.tvEvento);
            tvMes = itemView.findViewById(R.id.tvMes);
            tvMunicipio = itemView.findViewById(R.id.tvMunicipio);
            icono = (ImageView) itemView.findViewById(R.id.ivEvento);
            getContext = itemView.getContext();

        }
    }
}

