package com.example.abdialam.marketresto.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.adapter.PagerAdapterTabFavorit;


public class FavoriteFragment extends Fragment {





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        final TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        final ViewPager viewPager = view.findViewById(R.id.pager);

        PagerAdapterTabFavorit pagerAdapterTabFavorit = new PagerAdapterTabFavorit(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapterTabFavorit);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;




    }

}