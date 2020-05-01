package app.turiatlantico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static app.turiatlantico.R.drawable.icon_logo;

public class Detalle extends AppCompatActivity {
    private String direccion = "",nombre = "", tipo = "",municipio = "",detalle = "", id="",Nom_Tipo="", geolocalizacion="",nom_ubicacion="";
    private int zoom = 15;
    private ImageView image;
    private static  final String GEOLOCALIZACION = "Coordenadas";
    private ProgressBar bar;
    TextView tx_nombre,tx_direccion,tx_tipo,tx_detalle,tx_municipio;
    Button aceptar,maps;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        tx_nombre = (TextView)findViewById(R.id.Nombre);
        tx_direccion = (TextView)findViewById(R.id.Dirrecion);
        tx_tipo = (TextView)findViewById(R.id.Tipo);
        tx_detalle = (TextView)findViewById(R.id.Detalles);
        tx_municipio = (TextView)findViewById(R.id.Municipio);
        aceptar = (Button)findViewById(R.id.btn_aceptar);
        maps = (Button)findViewById(R.id.btn_maps);
        image = (ImageView) findViewById(R.id.Foto);
        bar = (ProgressBar) findViewById(R.id.progress);
        recibirDatos();
        CargarDatos();
        CargarImagen();
    }

    private void ObtenerUbicaion() {
        zoom = 12;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection(GEOLOCALIZACION).document(nom_ubicacion);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       geolocalizacion = (String) document.getData().get("Geolocalizacion");
                    } else {
                        geolocalizacion = "10.6966159, -74.8741045";
                        Toast toast = Toast.makeText(Detalle.this,
                                "No se encuentra la ubicaion de "+nom_ubicacion,
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {

                    Toast toast = Toast.makeText(Detalle.this,
                            "get failed with "+task.getException(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    private void CargarImagen() {
        final StorageReference reference = FirebaseStorage.getInstance().getReference();

        reference.child(Nom_Tipo+"/"+id+"/"+nombre+".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(Detalle.this)
                                .load(uri)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                                Target<Drawable> target, boolean isFirstResource) {
                                        bar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model,
                                                                   Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        bar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                image.setImageResource(R.drawable.icon_imagen);
                bar.setVisibility(View.GONE);
                Toast toast = Toast.makeText(Detalle.this,
                        "Hubo un error al cargar la imagen "+nombre,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private void CargarDatos() {

        if ("Operadores".equalsIgnoreCase(Nom_Tipo)){
            tx_tipo.setText(tipo);
            tx_municipio.setText(municipio);
            nom_ubicacion = nombre;
        } else if("Eventos".equalsIgnoreCase(Nom_Tipo)){
            tx_tipo.setText("Municipio: "+tipo);
            tx_municipio.setText("Se celebra en el mes de: "+municipio);
            nom_ubicacion=tipo;
            ObtenerUbicaion();
        }else if("Atractivos".equalsIgnoreCase(Nom_Tipo)){
            tx_tipo.setText(tipo);
            tx_municipio.setText("Municipio: "+municipio);
            nom_ubicacion = municipio;
            ObtenerUbicaion();
        }
            tx_nombre.setText(nombre);
            tx_direccion.setText(direccion);
            tx_detalle.setText(detalle);

    }

    private void recibirDatos() {
        Bundle extra = getIntent().getExtras();
        direccion = extra.getString("dirrecion");
        nombre = extra.getString("nombre");
        tipo = extra.getString("tipo");
        municipio = extra.getString("municipio");
        detalle = extra.getString("detalle");
        id = extra.getString("id");
        Nom_Tipo = extra.getString("Nom_Tipo");
        geolocalizacion = extra.getString("Ubicacion");

    }

    public void cerrar (View view){
        finish();
    }

    public void Maps_google (View view){
        Intent intent = new Intent(this, MapsOperadores.class);
            intent.putExtra("Geolocalizacion",geolocalizacion);
            intent.putExtra("Operador", nom_ubicacion);
            intent.putExtra("Distancia", zoom);
        startActivity(intent);
    }


}
