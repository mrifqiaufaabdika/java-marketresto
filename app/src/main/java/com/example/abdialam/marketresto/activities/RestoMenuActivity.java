package com.example.abdialam.marketresto.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;

import com.example.abdialam.marketresto.adapter.MenuAdapter;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.Menu;
import com.example.abdialam.marketresto.models.Restoran;
import com.example.abdialam.marketresto.responses.ResponseMenu;
import com.example.abdialam.marketresto.responses.ResponseRestoran;
import com.example.abdialam.marketresto.rest.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoMenuActivity  extends AppCompatActivity{

    private static final String TAG = "RestoMenuActivity";


    RecyclerView mRecylerView;
    private MenuAdapter mAdapter;

    private List<Menu> data = new ArrayList<>();
    ApiService mApiService ;
    Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_menu);
        Log.d(TAG, "onCreate: started");

        mApiService = ServerConfig.getAPIService();
        mRecylerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mContext = this;

        mAdapter = new MenuAdapter(mContext,data);
        RecyclerView.LayoutManager layoutManager    = new LinearLayoutManager(mContext);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setItemAnimator(new DefaultItemAnimator());
        mRecylerView.setAdapter(mAdapter);

        getIncomingIntent();
    }

    private void getIncomingIntent (){
        Log.d(TAG, "getIncomingIntent: checing for incoming intent");
        if(getIntent().hasExtra("Resto")){
            Log.d(TAG, "getIncomingIntent: found intent extras");
            Restoran namaResto = (Restoran)getIntent().getSerializableExtra("Resto");

            setValue(namaResto.getIdRestoran().toString());
        }
    }

    private void setValue(String id_restorant){



       mApiService.getRestoranMenuById(id_restorant).enqueue(new Callback<ResponseMenu>() {
           @Override
           public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
               String value = response.body().getValue();
               if(value.equals("1")){
                   data = response.body().getData();
                   mAdapter = new MenuAdapter(mContext,data);
                   mRecylerView.setAdapter(mAdapter);
                   Toast.makeText(mContext,"ok",Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResponseMenu> call, Throwable t) {

           }
       });


    }

}
