package app.turiatlantico.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
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
import app.turiatlantico.Login;
import app.turiatlantico.R;
import app.turiatlantico.pojos.Atractivo;
import app.turiatlantico.recycler.AtractivosRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListAtractivos extends Fragment {
    private static  final String ATRACTIVO = "Atractivos";
    AtractivosRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Atractivo> lisAtractivos = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw=inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) vw.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestAtractivo();
        return vw;
    }

    private void requestAtractivo() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference myRef = firestore.collection(ATRACTIVO);

        myRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    lisAtractivos.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // String even = (String) document.getData().get("Nombre");
                        //Toast.makeText(getContext(), "Nombre: "+even, Toast.LENGTH_LONG).show();
                        Atractivo atractivo = new Atractivo();
                        atractivo.setId(document.getId());
                        atractivo.setDescripcion((String) document.getData().get("Descripcion"));
                        atractivo.setDirrecion((String) document.getData().get("Dirrecion"));
                        atractivo.setNombre((String) document.getData().get("Nombre"));
                        atractivo.setTipo((String) document.getData().get("Tipo"));
                        atractivo.setMunicipio((String) document.getData().get("Municipio"));
                        listAtractivos(atractivo);
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to read value." + task.getException(), Toast.LENGTH_LONG).show();
                }

            }
        });
/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("atractivos");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot snapShot: dataSnapshot.getChildren()){
                    Atractivo atr = snapShot.getValue(Atractivo.class);
                    listAtractivos(atr);
                    //Toast.makeText(getContext(), "Encontrado "+eve.getEvento(), Toast.LENGTH_LONG).show();
                }
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, ""+ value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getContext(), "Failed to read value.", Toast.LENGTH_LONG).show();

            }
        });
*/

    }
    private void listAtractivos(final Atractivo atr ) {
        lisAtractivos.add(atr);
        adapter= new AtractivosRecyclerAdapter(lisAtractivos);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), Detalle.class);
                intent.putExtra("municipio",lisAtractivos.get
                        (recyclerView.getChildAdapterPosition(view)).getMunicipio());
                intent.putExtra("nombre",lisAtractivos.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                intent.putExtra("detalle",lisAtractivos.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                intent.putExtra("dirrecion","Direccion: "+lisAtractivos.get
                        (recyclerView.getChildAdapterPosition(view)).getDirrecion());
                intent.putExtra("tipo",lisAtractivos.get(recyclerView.getChildAdapterPosition(view)).getTipo());
                intent.putExtra("id",lisAtractivos.get(recyclerView.getChildAdapterPosition(view)).getId());
                intent.putExtra("Nom_Tipo","Atractivos");
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
