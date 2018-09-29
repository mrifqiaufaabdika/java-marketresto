package com.example.abdialam.marketresto.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.adapter.KategoriDynamicFragmentAdapter;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.Kategori;
import com.example.abdialam.marketresto.models.Menu;
import com.example.abdialam.marketresto.models.Restoran;
import com.example.abdialam.marketresto.responses.ResponseMenu;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private Restoran resto;
    private ApiService mApiService;
    private List<Menu> menuList = new ArrayList<>();
    private List<Kategori> kategoriList = new ArrayList<>();
    ProgressDialog progressDialog;
    String oprasional;



    Context mContext;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mApiService = ServerConfig.getAPIService();
        viewPager = findViewById(R.id.viewpager);
        mTabLayout =  findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mContext= this;

        initViews();

        getIncomingIntent();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Menu "+resto.getRestoranNama());





        progressDialog = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);
        oprasional = resto.getRestoranOperasional().toString();




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(mContext,CartListActivity.class);
//                intent.putExtra("Resto", resto);
                startActivity(intent);
            }
        });
    }




    private void initViews(){
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        setDynamicFragmentToTabLayout();

    }

    private void setDynamicFragmentToTabLayout() {
        int jmlKate = kategoriList.size();


        for (int i = 0; i < jmlKate; i++) {

            mTabLayout.addTab(mTabLayout.newTab().setText(kategoriList.get(i).getKategoriNama()));
        }


        KategoriDynamicFragmentAdapter mDynamicFragmentAdapter = new KategoriDynamicFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(),menuList,kategoriList,oprasional);
        viewPager.setAdapter(mDynamicFragmentAdapter);
        viewPager.setCurrentItem(0);
    }


    private void getIncomingIntent (){

        if(getIntent().hasExtra("Resto")){

            resto = (Restoran)getIntent().getSerializableExtra("Resto");
            String id_restoran = resto.getIdRestoran().toString();
            setValue(id_restoran);

//            Bundle bundle = new Bundle();
//            String id_resto = resto.getIdRestoran().toString();
//            bundle.putString("id_resto",id_resto);
//            RestoMenuFragment restoMenuFragment = new RestoMenuFragment();
//            restoMenuFragment.setArguments(bundle);
//            loadFragment(restoMenuFragment);
        }
    }


    private void setValue(String id_restorant){

        mApiService.getRestoranMenuById(id_restorant).enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    progressDialog.dismiss();
                    menuList = response.body().getData();
                    kategoriList = response.body().getKategori();
                    initViews();
//                    mAdapter = new MenuAdapter(getActivity(),data,listener);
//                    mRecylerView.setAdapter(mAdapter);
//                    Toast.makeText(getContext(),"ok",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }

}



