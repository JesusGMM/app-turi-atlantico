package app.turiatlantico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private static final String CORREO_PASSWORD = "password";
    private static final String SALIR = "Salir";
    private static final String COMENZAR = "Comenzar";
    TextView txMensaje;
    Button boton_iniciar;
    TextView txNombreBoton;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                        validarcorreo();
                        user.sendEmailVerification();
                    } else {
                        onSetDataUser(user.getDisplayName(), user.getEmail(), user.getProviderData().get(1).getProviderId());
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
                            //.setLogo(R.drawable.img_multi_login)
                            .build(), LOGIN);
                }

            }
        };
    }
    private void limpiar() {
        txMensaje.setText("");
        txNombreBoton.setText("");
        boton_iniciar.setVisibility(View.INVISIBLE);
    }

    private void validarcorreo() {
        txMensaje.setText(R.string.msj_correo_enviado);
        txNombreBoton.setText(R.string.msj_validar_correo);
        boton_iniciar.setText(R.string.salir);
    }

    private void onSetDataUser(String Name, String email, String provedor) {
        txMensaje.setText(R.string.descripcion);
        txNombreBoton.setText(R.string.bienvenidos);
        boton_iniciar.setText(R.string.Iniciar);
    }


    public void Comenzar (View v) {
        String nombre = boton_iniciar.getText().toString();
        if (nombre.equalsIgnoreCase(SALIR)){
            AuthUI.getInstance().signOut(this);
        }
        else if (nombre.equalsIgnoreCase(COMENZAR)){
            Intent intent = new Intent (v.getContext(), MainActivity.class);
            startActivityForResult(intent, 0);

        }else {
            Toast toast = Toast.makeText(this, "Se produjo un error vuelvalo a intentar", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
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
