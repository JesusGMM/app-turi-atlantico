package app.turiatlantico;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.turiatlantico.fragments.ListAtractivos;
import app.turiatlantico.fragments.ListEventos;
import app.turiatlantico.fragments.ListOperadores;

public class MainActivity extends AppCompatActivity {
    private static final String EVENTOS = "Eventos";
    private static final String ATRACTIVOS = "Atractivos";
    private static final String OPERADORES = "Operadores";
    private static String OP="0";
    private FirebaseAuth mfirebaseAuth;

    TabLayout tabLayout;
    ViewPager viewPager;
    String datos;
    TextView txPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mfirebaseAuth.getCurrentUser();
        txPerfil = (TextView)findViewById(R.id.nombre_perfil);
        tabLayout=(TabLayout)findViewById(R.id.tabsLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        txPerfil.setText(user.getDisplayName());
        setUpViewPager(viewPager);
}


    private void setUpViewPager(ViewPager viewPager) {
        TabViewPagerAdapter tabViewPagerAdapter=  new TabViewPagerAdapter(getSupportFragmentManager());
        tabViewPagerAdapter.addFragment(new ListEventos(),EVENTOS);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                Intent intent = new Intent (this, Login.class);
                intent.putExtra("cerrar","cerrar_seccion");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
