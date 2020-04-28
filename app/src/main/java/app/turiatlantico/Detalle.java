package app.turiatlantico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static app.turiatlantico.R.drawable.icon_logo;

public class Detalle extends AppCompatActivity {
    private String direccion = "",nombre = "", tipo = "",municipio = "",detalle = "", id="",Nom_Tipo="";
    private Button aceptar,maps;
    private ImageView image;
    private StorageReference mStorageReference;
    TextView tx_nombre,tx_direccion,tx_tipo,tx_detalle,tx_municipio;
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
        recibirDatos();
        CargarDatos();
        CargarImagen();

    }

    private void CargarImagen() {
        final StorageReference reference = FirebaseStorage.getInstance().getReference();

        reference.child(Nom_Tipo+"/"+id+"/"+nombre+".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(Detalle.this)
                                .load(uri)
                                .into(image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                image.setImageResource(R.drawable.icon_imagen);
                Toast toast = Toast.makeText(Detalle.this,
                        "Hubo un error al cargar la imagen "+nombre,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private void CargarDatos() {
        tx_nombre.setText(nombre);
        tx_tipo.setText(tipo);
        tx_municipio.setText(municipio);
        tx_direccion.setText(direccion);
        tx_detalle.setText(detalle);
        if ("Operadores".equalsIgnoreCase(Nom_Tipo)){
            tx_detalle.setText("");
        }
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
    }

    public void cerrar (View view){
        finish();
    }

    public void Maps_google (View view){
        Intent intent = new Intent(this, MapsOperadores.class);
        intent.putExtra("Geolocalizacion",detalle);
        intent.putExtra("Operador",nombre);
        startActivity(intent);
    }



}
