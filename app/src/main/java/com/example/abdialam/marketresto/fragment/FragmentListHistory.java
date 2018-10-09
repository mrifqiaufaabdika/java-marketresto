package com.example.abdialam.marketresto.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.adapter.OrderAdapter;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.Pesan;
import com.example.abdialam.marketresto.responses.ResponsePesan;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.utils.SessionManager;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListHistory extends Fragment {

    Context mContext;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Pesan> pesanList;
    SessionManager sessionManager;
    HashMap<String,String> user;
    ApiService mApiService ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,container,false);
        mContext = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getUserDetail();
        mApiService = ServerConfig.getAPIService();
        getPesan();

        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        return view;
    }


    public void getPesan(){
        String id_konsumen = user.get(SessionManager.ID_USER);
        mApiService.getOrderProces(id_konsumen).enqueue(new Callback<ResponsePesan>() {
            @Override
            public void onResponse(Call<ResponsePesan> call, Response<ResponsePesan> response) {
                if(response.isSuccessful()){
                    String value = response.body().getValue();
                    if(value.equals("1")){
                        pesanList = response.body().getPesan();
                        adapter = new OrderAdapter(mContext,pesanList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePesan> call, Throwable t) {

            }
        });

    }
}
