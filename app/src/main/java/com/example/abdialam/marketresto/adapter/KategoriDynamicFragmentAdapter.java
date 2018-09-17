package com.example.abdialam.marketresto.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.abdialam.marketresto.fragment.MenuFragment;

public class KategoriDynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private String id_resto;
    //private List<Menu> menuList = new ArrayList<>();
    //private List<Kategori> kategoriList = new ArrayList<>();

    public KategoriDynamicFragmentAdapter(FragmentManager fm, int NumOfTabs, String id_resto) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.id_resto =id_resto;

    }

    @Override
    public Fragment getItem(int position) {

        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("id_resto",id_resto);
        Fragment frag = MenuFragment.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
