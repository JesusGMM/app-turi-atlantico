package app.turiatlantico.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.turiatlantico.MapsOperadores;
import app.turiatlantico.R;
import app.turiatlantico.pojos.Operador;
import app.turiatlantico.recycler.OperadoresRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOperadores extends Fragment  {
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
    private void requestOperadores() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("operadoresTuristicos");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapShot: dataSnapshot.getChildren()){
                    Operador ope= snapShot.getValue(Operador.class);
                    listOperadores(ope);

                }

                /*String value = dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, ""+ value, Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getContext(), "Failed to read value.", Toast.LENGTH_LONG).show();

            }
        });


    }
    private void listOperadores(final Operador ope) {
        lisOperadores.add(ope);
        adapter= new OperadoresRecyclerAdapter(lisOperadores);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getContext(), MapsOperadores.class);
                intent.putExtra("Geolocalizacion",
                        lisOperadores.get(recyclerView.getChildAdapterPosition(view)).getGeolocalizacion());
                intent.putExtra("Operador",
                        lisOperadores.get(recyclerView.getChildAdapterPosition(view)).getOperador_turistico());
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);

    }


}
