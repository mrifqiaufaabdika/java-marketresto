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
import android.widget.TextView;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.adapter.MenuAdapter;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.Kategori;
import com.example.abdialam.marketresto.models.Menu;
import com.example.abdialam.marketresto.responses.ResponseMenu;
import com.example.abdialam.marketresto.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    ApiService mApiService;
    private RecyclerView mRecylerView;
    private MenuAdapter mAdapter;
    private List<Menu> menuList = new ArrayList<>();
    private List<Menu> menuListTemp = new ArrayList<>();
    private List<Kategori> kategoriList = new ArrayList<>();
    private MenuAdapter.OnItemClickListener listener;
    TextView pos;
    int position;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manu, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mApiService = ServerConfig.getAPIService();

        mRecylerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        listener =this;
        mAdapter = new MenuAdapter(getActivity(),menuList,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setItemAnimator(new DefaultItemAnimator());
        mRecylerView.setAdapter(mAdapter);
        pos = (TextView) view.findViewById(R.id.position);

        setValue(getArguments().getString("id_resto"));

        position = getArguments().getInt("position");




    }


    @Override
    public void onItemCliked(View v, int position) {
        Menu menuItem = menuListTemp.get(position);
        Bundle argument = new Bundle();
        argument.putSerializable("selectedItem",menuItem);
        DialogPlaceOrderFragment placeOrderFragment = new DialogPlaceOrderFragment();
        placeOrderFragment.setArguments(argument);
        placeOrderFragment.show(getFragmentManager(), DialogPlaceOrderFragment.ARG_ITEM_ID);
    }

    private void setValue(String id_restorant){

        mApiService.getRestoranMenuById(id_restorant).enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    menuList = response.body().getData();
                    kategoriList = response.body().getKategori();

//                    mAdapter = new MenuAdapter(getActivity(),menuList,listener);
//                    mRecylerView.setAdapter(mAdapter);
//                    Toast.makeText(getContext(),"ok",Toast.LENGTH_SHORT).show();
                    setByKategori();
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {

            }
        });


    }

    public void setByKategori(){


        for (int i = 0; i < kategoriList.size() ; i++) {
            if(position == i){
                for (int j = 0; j < menuList.size(); j++) {
                    if (menuList.get(j).getMenuKategoriId().toString().equals(kategoriList.get(i).getMenuKategoriId().toString())){
                        menuListTemp.add(menuList.get(j));
                    }
                }
            }
        }
        if(position == 0){
            pos.setText("satu");
        }else if (position ==1){
            pos.setText("dua");
        }else {
            pos.setText("tiga");
        }
        mAdapter = new MenuAdapter(getActivity(),menuListTemp,listener);
        mRecylerView.setAdapter(mAdapter);

    }
}

