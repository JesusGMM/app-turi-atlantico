package app.turiatlantico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;

import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;

import app.turiatlantico.fragments.ListAtractivos;
import app.turiatlantico.fragments.ListEventos;
import app.turiatlantico.fragments.ListOperadores;

public class MainActivity extends AppCompatActivity {
    private static final String EVENTOS = "Eventos";
    private static final String ATRACTIVOS = "Atractivos";
    private static final String OPERADORES = "Operadores";
    private static String OP="0";
    private static final int LOGIN = 123;
    private static final String CORREO_PASSWORD = "password";
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    TabLayout tabLayout;
    ViewPager viewPager;
    String datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                  mostrardatos();
                } else {
                   AuthUI.IdpConfig facebookIdp = new AuthUI.IdpConfig.FacebookBuilder()
                           .build();
                    AuthUI.IdpConfig googleId = new AuthUI.IdpConfig.GoogleBuilder().build();

                    AuthUI.IdpConfig TwitterId = new AuthUI.IdpConfig.TwitterBuilder().build();

                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),facebookIdp, googleId, TwitterId))
                            //.setTheme(R.style.GreenTheme)
                            //.setLogo(R.drawable.img_multi_login)
                           .build(), LOGIN);
                }

            }
        };
}

    private void mostrardatos() {
        tabLayout=(TabLayout)findViewById(R.id.tabsLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager(viewPager);
    }


    private void setUpViewPager(ViewPager viewPager) {
        TabViewPagerAdapter tabViewPagerAdapter=  new TabViewPagerAdapter(getSupportFragmentManager());
        tabViewPagerAdapter.addFragment(new ListEventos(),"Eventos");
        tabViewPagerAdapter.addFragment(new ListAtractivos(),ATRACTIVOS);
        tabViewPagerAdapter.addFragment(new ListOperadores(),OPERADORES);
        viewPager.setAdapter(tabViewPagerAdapter);
        Bundle parametros = this.getIntent().getExtras();

        if(parametros !=null){
                datos = parametros.getString("op");
            if(datos.equalsIgnoreCase("1")) {
                OP="1";
                return;

            }
            if(datos.equalsIgnoreCase("2")){
                OP="2";
                viewPager.setCurrentItem(2);
                return;
            }
            if(datos.equalsIgnoreCase("3")){
                OP="3";
                viewPager.setCurrentItem(1);
                return;
            }

            OP = "0";
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
