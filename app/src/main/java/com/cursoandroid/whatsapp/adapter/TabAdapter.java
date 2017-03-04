package com.cursoandroid.whatsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cursoandroid.whatsapp.fragment.ContatosFragment;
import com.cursoandroid.whatsapp.fragment.ConversasFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 04/03/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] titulos={"CONVERSAS","CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new ConversasFragment();
                break;
            case 1:
                fragment=new ContatosFragment();
                break;
        }
        return fragment ;
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titulos[position];
    }
}
