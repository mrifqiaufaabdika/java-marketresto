package com.example.abdialam.marketresto.fragment;

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
import com.example.abdialam.marketresto.adapter.RestorantAdapter;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.Restoran;
import com.example.abdialam.marketresto.responses.ResponseRestoran;
import com.example.abdialam.marketresto.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestorantAdapter adapter;
    private List<Restoran> data = new ArrayList<>();
    ApiService mApiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resto,container,false);
        mApiService = ServerConfig.getAPIService();
        addData();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        adapter = new RestorantAdapter(getActivity(),data);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);


        return view;
    }

    private void addData() {
        mApiService.getRestoran().enqueue(new Callback<ResponseRestoran>() {
            @Override
            public void onResponse(Call<ResponseRestoran> call, Response<ResponseRestoran> response) {
                String value = response.body().getStatus();
                if(value.equals("1")){
                    data = response.body().getData();
                    adapter = new RestorantAdapter(getActivity(),data);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseRestoran> call, Throwable t) {

            }
        });
    }


}
