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
import app.turiatlantico.pojos.Operador;

public class OperadoresRecyclerAdapter extends RecyclerView.Adapter<OperadoresRecyclerAdapter.OperadoresHolder>
        implements View.OnClickListener {

    ArrayList<Operador> listOperadores;
    View.OnClickListener listener;
    Context getContext;

    public OperadoresRecyclerAdapter(ArrayList<Operador> listOperadores) {
        this.listOperadores = listOperadores;
    }

    @NonNull
    @Override
    public OperadoresHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_operador,null,false);
        view.setOnClickListener(this);
        return new OperadoresHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OperadoresHolder operadoresHolder, final int i) {
        operadoresHolder.icono.setImageBitmap(null);
        final StorageReference reference = FirebaseStorage.getInstance().getReference();
        String Inicial = listOperadores.get(i).getNombre();
        Inicial = Inicial.substring(0,1);
        reference.child("Iconos/"+Inicial+".png").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext)
                                .load(uri)
                                .centerCrop()
                                .into(operadoresHolder.icono);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //image.setImageResource(R.drawable.icon_imagen);
                Toast toast = Toast.makeText(getContext,
                        "Hubo un error al cargar el icono "+ listOperadores.get(i).getNombre(),
                        Toast.LENGTH_SHORT);
                toast.show();
                operadoresHolder.icono.setImageResource(R.drawable.icon_imagen);
            }
        });
        operadoresHolder.tvOperadorTuristico.setText(listOperadores.get(i).getNombre());
        operadoresHolder.tvDireccionComercial.setText(listOperadores.get(i).getDireccion_comercial());
        operadoresHolder.tvTelefono.setText(listOperadores.get(i).getTelefono());

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

    public class OperadoresHolder extends RecyclerView.ViewHolder {
        TextView tvOperadorTuristico,tvTelefono,tvDireccionComercial;
        ImageView icono;
        public OperadoresHolder(@NonNull View itemView) {
            super(itemView);
            tvOperadorTuristico=(TextView)itemView.findViewById(R.id.tvOperadorTuristico);
            tvTelefono =(TextView)itemView.findViewById(R.id.tvTelefono);
            tvDireccionComercial=itemView.findViewById(R.id.tvDireccionComercial);
            icono = (ImageView) itemView.findViewById(R.id.ivOperador);
            getContext = itemView.getContext();
        }
    }
}