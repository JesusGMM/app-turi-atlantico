package app.turiatlantico.recycler;

import android.content.Context;
import android.net.Uri;
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
import app.turiatlantico.pojos.Atractivo;

public class AtractivosRecyclerAdapter extends RecyclerView.Adapter<AtractivosRecyclerAdapter.AtractivosHolder>
implements View.OnClickListener {
    ArrayList<Atractivo> listAtractivos;
    View.OnClickListener listener;
    Context getContext;

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
    public void onBindViewHolder(@NonNull final AtractivosHolder holder, final int i) {
        holder.icono.setImageBitmap(null);
        final StorageReference reference = FirebaseStorage.getInstance().getReference();
        String Inicial = listAtractivos.get(i).getNombre();
        Inicial = Inicial.substring(0,1);

        reference.child("Iconos/"+Inicial+".png").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext)
                                .load(uri)
                                .centerCrop()
                                .into(holder.icono);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.icono.setImageResource(R.drawable.icon_imagen);
                Toast toast = Toast.makeText(getContext,
                        "Hubo un error al cargar el icono "+ listAtractivos.get(i).getNombre(),
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        holder.tvNombreMunicipio.setText(listAtractivos.get(i).getMunicipio());
        holder.tvNombreSitio.setText(listAtractivos.get(i).getNombre());
        holder.tvTipo.setText(listAtractivos.get(i).getTipo());
    }

    @Override
    public int getItemCount() {
        return listAtractivos.size();
    }

    public class AtractivosHolder extends RecyclerView.ViewHolder {
        TextView tvNombreSitio,tvNombreMunicipio,tvTipo;
        ImageView icono;
        public AtractivosHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreSitio=(TextView)itemView.findViewById(R.id.tvNombreSitio);
            tvNombreMunicipio =(TextView)itemView.findViewById(R.id.tvNombreMunicipio);
            tvTipo=itemView.findViewById(R.id.tvTipo);
            icono = (ImageView) itemView.findViewById(R.id.ivSitio);
            getContext = itemView.getContext();
        }
    }
}
