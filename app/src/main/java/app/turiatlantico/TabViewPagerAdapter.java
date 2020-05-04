package app.turiatlantico;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentsList= new ArrayList<>();
    private final ArrayList<String> fragTitulos = new ArrayList<>();

    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentsList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    public void addFragment(Fragment fragment,String titulo){
        fragmentsList.add(fragment);
        fragTitulos.add(titulo);
    }
    @Override
    public CharSequence getPageTitle(int i){

        return fragTitulos.get(i);
    }

}
