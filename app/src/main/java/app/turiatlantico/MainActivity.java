package app.turiatlantico;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;



import app.turiatlantico.fragments.ListAtractivos;
import app.turiatlantico.fragments.ListEventos;
import app.turiatlantico.fragments.ListOperadores;

public class MainActivity extends AppCompatActivity {
    private static final String EVENTOS = "Eventos";
    private static final String ATRACTIVOS = "Atractivos";
    private static final String OPERADORES = "Operadores";
    private static String OP="0";



    TabLayout tabLayout;
    ViewPager viewPager;
    String datos;
    TextView txPerfil;
    ImageView Borrar_busqueda;
    EditText buscar;
    private static ListEventos listEventos = new ListEventos();
    private static ListAtractivos listAtractivos = new ListAtractivos();
    private static ListOperadores listOperadores = new ListOperadores();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txPerfil = (TextView)findViewById(R.id.nombre_perfil);
        tabLayout =(TabLayout)findViewById(R.id.tabsLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        Borrar_busqueda = (ImageView)findViewById(R.id.imageborrar);
        buscar = (EditText)findViewById(R.id.Ed_Buscar);
        buscar.setText("");
        listEventos.setBuscar(buscar.getText().toString());
        setUpViewPager(viewPager);

        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().equalsIgnoreCase("")) {
                    Borrar_busqueda.setVisibility(View.INVISIBLE);
                }
                else {
                    Borrar_busqueda.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listEventos.setBuscar(s.toString());
                    listAtractivos.setBuscar(s.toString());
                    listOperadores.setBuscar(s.toString());
                    setUpViewPager(viewPager);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {
                    Borrar_busqueda.setVisibility(View.INVISIBLE);
                }
                else {
                    Borrar_busqueda.setVisibility(View.VISIBLE);
                }
            }
        });

        Borrar_busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(buscar.getWindowToken(), 0);
                buscar.clearFocus();
            }
        });

}




    private void setUpViewPager(ViewPager viewPager) {
        TabViewPagerAdapter tabViewPagerAdapter=  new TabViewPagerAdapter(getSupportFragmentManager());
        tabViewPagerAdapter.addFragment(listEventos, EVENTOS);
        tabViewPagerAdapter.addFragment(listAtractivos, ATRACTIVOS);
        tabViewPagerAdapter.addFragment(listOperadores, OPERADORES);
        viewPager.setAdapter(tabViewPagerAdapter);

        /*Bundle parametros = this.getIntent().getExtras();

        if(parametros != null){
                datos = parametros.getString("op");
            if(datos.equalsIgnoreCase("1")) {
                Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
                OP="1";
                return;

            }
            if(datos.equalsIgnoreCase("2")){
                OP="2";
                Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
                viewPager.setCurrentItem(2);
                return;
            }
            if(datos.equalsIgnoreCase("3")){
                OP="3";
                Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
                viewPager.setCurrentItem(1);
                return;
            }
            OP = "0";
        }*/
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
