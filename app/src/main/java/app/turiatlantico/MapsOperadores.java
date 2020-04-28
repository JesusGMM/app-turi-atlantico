package app.turiatlantico;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsOperadores extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String geo="";
    String nombreOperador="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_operadores);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();
        geo = bundle.getString("Geolocalizacion");
        nombreOperador = bundle.getString("Operador");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        geo=geo.replace("(","");
        geo=geo.replace(")","");
        geo=geo.replace(",","");

        String[] parts = geo.split("-");

        Double  part1 = Double.parseDouble(parts[0]);
        Double part2 = Double.parseDouble(parts[1]);
        LatLng operador = new LatLng(part1, -part2);
        mMap.addMarker(new MarkerOptions().position(operador).title(nombreOperador)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(operador,15));
    }
}
