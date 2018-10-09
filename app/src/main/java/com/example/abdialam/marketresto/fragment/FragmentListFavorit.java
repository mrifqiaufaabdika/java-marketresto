package com.example.abdialam.marketresto.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.adapter.FavoritAdapter;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.Favorit;
import com.example.abdialam.marketresto.responses.ResponseFavorit;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListFavorit extends Fragment {

    private RecyclerView recyclerView;
    private FavoritAdapter favoritAdapter;
    private List<Favorit> favoritList = new ArrayList<>();
    ApiService mApiService;
    Context mContext;

    HashMap<String,String> user;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_favorite,container,false);
        mApiService = ServerConfig.getAPIService();
        mContext = getActivity();
        sessionManager = new SessionManager(mContext);
        user =sessionManager.getUserDetail();
        String id_konsumen = user.get(SessionManager.ID_USER);
        setValue(id_konsumen);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());





        return view;
    }



    private void setValue(String id_konsumen) {
        mApiService.getFavorit(id_konsumen).enqueue(new Callback<ResponseFavorit>() {
            @Override
            public void onResponse(Call<ResponseFavorit> call, Response<ResponseFavorit> response) {
                if(response.isSuccessful()){
                    favoritList =response.body().getFavorit();
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if(value.equals("1")){
                        favoritAdapter = new FavoritAdapter(mContext,favoritList);
                        recyclerView.setAdapter(favoritAdapter);
                    }else {
                        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFavorit> call, Throwable t) {
                Toast.makeText(getActivity(),R.string.lostconnection,Toast.LENGTH_SHORT).show();
            }
        });


    }


}
