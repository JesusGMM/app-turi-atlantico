package app.turiatlantico.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

import app.turiatlantico.Detalle;
import app.turiatlantico.R;
import app.turiatlantico.pojos.Evento;
import app.turiatlantico.recycler.EventosRecyclerAdapter;




/**
 * A simple {@link Fragment} subclass.
 */

public class ListEventos extends Fragment {
 private static  final String EVENTO = "Eventos";
    private String buscar = "";
    EventosRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList <Evento>  lisEventos = new ArrayList<>();

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) vw.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestEventos();
        return vw;
    }

    private void requestEventos() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference myRef = firestore.collection(EVENTO);

        myRef.orderBy("Nombre").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    lisEventos.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (buscar.equalsIgnoreCase("")) {
                          CrearObjeto(document);
                        }else {
                            String texto = (String) document.getData().get("Nombre");
                            if (texto.toLowerCase().contains(buscar.toLowerCase()))
                                CrearObjeto(document);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to read value." + task.getException(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }


     public void CrearObjeto(QueryDocumentSnapshot document){
         Evento eve = new Evento();
         eve.setId(document.getId());
         eve.setDescripcion((String) document.getData().get("Descripcion"));
         eve.setDirrecion((String) document.getData().get("Dirrecion"));
         eve.setNombre((String) document.getData().get("Nombre"));
         eve.setMes((String) document.getData().get("fecha"));
         listEventos(eve);
     }
    private void listEventos(Evento eve) {

        lisEventos.add(eve);
        adapter = new EventosRecyclerAdapter(lisEventos);
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), Detalle.class);
                intent.putExtra("nombre",lisEventos.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                intent.putExtra("tipo",lisEventos.get
                        (recyclerView.getChildAdapterPosition(view)).getDirrecion());
                intent.putExtra("dirrecion","");
                intent.putExtra("detalle",lisEventos.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                intent.putExtra("municipio",lisEventos.
                        get(recyclerView.getChildAdapterPosition(view)).getMes());
                intent.putExtra("id",lisEventos.get(recyclerView.getChildAdapterPosition(view)).getId());
                intent.putExtra("Nom_Tipo","Eventos");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
