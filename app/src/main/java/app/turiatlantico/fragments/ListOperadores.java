package app.turiatlantico.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

import app.turiatlantico.Detalle;
import app.turiatlantico.MapsOperadores;
import app.turiatlantico.R;
import app.turiatlantico.pojos.Operador;
import app.turiatlantico.recycler.OperadoresRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOperadores extends Fragment  {
    private static  final String OPERADORES = "Operadores";
    private String buscar = "";
    OperadoresRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Operador> lisOperadores = new ArrayList<>();
    private GoogleMap mMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw=inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) vw.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestOperadores();
        return vw;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    private void requestOperadores() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference myRef = firestore.collection(OPERADORES);

        myRef.orderBy("Nombre").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    lisOperadores.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (buscar.equalsIgnoreCase("")) {
                            CrearObjeto(document);
                        }else {
                            String texto = (String) document.getData().get("Nombre");
                            int i = buscar.length();
                            String inicial = texto.substring(0,i);
                            if (inicial.equalsIgnoreCase(buscar)){
                                CrearObjeto(document);
                            }
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to read value." + task.getException(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
    public void CrearObjeto(QueryDocumentSnapshot document){
        Operador operador = new Operador();
        operador.setId(document.getId());
        operador.setDireccion_comercial((String) document.getData().get("Dirrecion"));
        operador.setTelefono((String) document.getData().get("Telefono"));
        operador.setNombre((String) document.getData().get("Nombre"));
        operador.setGeolocalizacion((String) document.getData().get("Geolocalizacion"));
        listOperadores(operador);


    }
    private void listOperadores(final Operador ope) {
        lisOperadores.add(ope);
        adapter= new OperadoresRecyclerAdapter(lisOperadores);
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), Detalle.class);
                intent.putExtra("nombre",lisOperadores.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                intent.putExtra("tipo","Municipio: Barranquilla");
                intent.putExtra("dirrecion","Dirrecion: "+lisOperadores.get
                        (recyclerView.getChildAdapterPosition(view)).getDireccion_comercial());
                intent.putExtra("Ubicacion",lisOperadores.get(recyclerView.getChildAdapterPosition(view)).getGeolocalizacion());
                intent.putExtra("municipio","Telefono: "+lisOperadores.
                        get(recyclerView.getChildAdapterPosition(view)).getTelefono());
                intent.putExtra("id",lisOperadores.get(recyclerView.getChildAdapterPosition(view)).getId());
                intent.putExtra("Nom_Tipo","Operadores");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

    }


}
