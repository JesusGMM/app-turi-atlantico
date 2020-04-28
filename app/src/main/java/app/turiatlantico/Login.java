package app.turiatlantico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Login extends AppCompatActivity {
    private static final int LOGIN = 123;
    private static final int PERMISSION = 0;
    private static final String CORREO_PASSWORD = "password";
    private static final String SALIR = "Salir";
    private static final String COMENZAR = "Comenzar";
    private int valida = 0;
    private String seccion = "";
    TextView txMensaje;
    Button boton_iniciar;
    TextView txNombreBoton;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            seccion = extra.getString("cerrar");
        }

        txMensaje = (TextView)findViewById(R.id.tx_mensaje);
        txNombreBoton = (TextView)findViewById(R.id.tx_nombre_boton);
        boton_iniciar = (Button)findViewById(R.id.iniciar);

        mfirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    boton_iniciar.setVisibility(View.VISIBLE);
                    if (user.getProviderData().get(1).getProviderId().equalsIgnoreCase(CORREO_PASSWORD) &&
                            !user.isEmailVerified()) {
                        msj_correo_sin_validar();
                        user.sendEmailVerification();
                    } else {
                        if ((!"cerrar_seccion".equalsIgnoreCase(seccion)) && valida == 0 &&
                                ActivityCompat.checkSelfPermission(getApplicationContext(),
                                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else
                            {
                            msj_inicio_seccion();
                            }
                    }
                } else {
                    limpiar();
                    AuthUI.IdpConfig facebookIdp = new AuthUI.IdpConfig.FacebookBuilder()
                            .build();
                    AuthUI.IdpConfig googleId = new AuthUI.IdpConfig.GoogleBuilder().build();

                    AuthUI.IdpConfig TwitterId = new AuthUI.IdpConfig.TwitterBuilder().build();

                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder()
                                    .build(),facebookIdp, googleId, TwitterId))
                            //.setTheme(R.style.GreenTheme)
                            .setLogo(R.drawable.icon_login)
                            .build(), LOGIN);
                }
            }
        };
        if("cerrar_seccion".equalsIgnoreCase(seccion))
            AuthUI.getInstance().signOut(this);
    }


    private void limpiar() {
        txMensaje.setText("");
        txNombreBoton.setText("");
        boton_iniciar.setVisibility(View.INVISIBLE);
    }

    private void msj_correo_sin_validar() {
        txMensaje.setText(R.string.msj_correo_enviado);
        txNombreBoton.setText(R.string.msj_validar_correo);
        boton_iniciar.setText(R.string.salir);
    }

    private void msj_inicio_seccion() {
        txMensaje.setText(R.string.descripcion);
        txNombreBoton.setText(R.string.bienvenidos);
        boton_iniciar.setText(R.string.Iniciar);
    }


    public void Comenzar (View v) {
        String nombre = boton_iniciar.getText().toString();
        if (nombre.equalsIgnoreCase(SALIR))
        {
            AuthUI.getInstance().signOut(this);
        }
        else if (nombre.equalsIgnoreCase(COMENZAR))
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION);
            }
            else
                {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        }
        else
            {
                toast = Toast.makeText(this, "Se produjo un error vuelvalo a intentar", Toast.LENGTH_SHORT);
                toast.show();
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN){
            if (resultCode == RESULT_OK){
                seccion = "seccion";
                valida = 1;
            } else {
               finishAffinity();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PERMISSION == requestCode)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else
                {
                    toast = Toast.makeText(this,
                            "Debe Activar El Permiso De Ubicación Para Poder Usar Nuestra Aplicación",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mfirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mfirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
