package app.turiatlantico.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.turiatlantico.R;
import app.turiatlantico.pojos.Atractivo;
import app.turiatlantico.recycler.AtractivosRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListAtractivos extends Fragment {
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
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("atractivos");
        ///myRef.setValue("Hello, World!");
        // Read from the database
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
    private void listAtractivos(Atractivo atr ) {
        lisAtractivos.add(atr);
        adapter= new AtractivosRecyclerAdapter(lisAtractivos);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Selección: "+lisAtractivos.get
                                (recyclerView.getChildAdapterPosition(view))
                                .getNombresitio(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
