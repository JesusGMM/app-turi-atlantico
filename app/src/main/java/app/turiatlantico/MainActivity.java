package app.turiatlantico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import app.turiatlantico.fragments.ListAtractivos;
import app.turiatlantico.fragments.ListEventos;
import app.turiatlantico.fragments.ListOperadores;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;

    ViewPager viewPager;
    String datos;
    private static String OP="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout)findViewById(R.id.tabsLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        TabViewPagerAdapter tabViewPagerAdapter=  new TabViewPagerAdapter(getSupportFragmentManager());
        tabViewPagerAdapter.addFragment(new ListEventos(),"Eventos");
        tabViewPagerAdapter.addFragment(new ListAtractivos(),"Atractivos");
        tabViewPagerAdapter.addFragment(new ListOperadores(),"Operadores");
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

            OP="0";
        }
    }

}
