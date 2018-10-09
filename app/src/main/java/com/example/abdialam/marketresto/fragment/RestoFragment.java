package com.example.abdialam.marketresto.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.activities.CartListActivity;
import com.example.abdialam.marketresto.activities.MapsActivity;
import com.example.abdialam.marketresto.activities.SettingActivity;
import com.example.abdialam.marketresto.adapter.RestorantAdapter;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.Restoran;
import com.example.abdialam.marketresto.responses.ResponseRestoran;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.utils.GPSTracker;
import com.example.abdialam.marketresto.utils.SessionManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestorantAdapter adapter;
    private List<Restoran> data = new ArrayList<>();
    ApiService mApiService;
    TextView textview;
    ImageButton openmap;
    Context mContext;
    GPSTracker gpsTracker;
    ImageView mError;
    String addressLine,latlng;
    SessionManager sessionManager;
    HashMap<String,String> locationSession;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resto,container,false);
        mContext = getActivity();
        sessionManager = new SessionManager(mContext);
        textview = (TextView)view.findViewById(R.id.tvLokasiAnda);
        mApiService = ServerConfig.getAPIService();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        openmap = (ImageButton) view.findViewById(R.id.openmap);
        mError = (ImageView) view.findViewById(R.id.error);

        locationSession = sessionManager.getLocation();




        openmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(latlng == null){
                  //  gpsTracker.showSettingsAlert();
                    Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(mContext, MapsActivity.class);
                    intent.putExtra("location", latlng);
                    startActivity(intent);
                }
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(mContext,"on resume",Toast.LENGTH_SHORT).show();
        gpsTracker = new GPSTracker(mContext);
        if(gpsTracker.canGetLocation()){
            //Gone Error
            mError.setVisibility(View.GONE);

            Bundle arguments = getArguments();
            if(arguments != null && arguments.containsKey("aksi")){
                textview.setText(  arguments.getString("aksi"));
                latlng = locationSession.get(SessionManager.LATLANG);
            }else {

                //ambil line address
                addressLine = gpsTracker.getAddressLine(mContext);
                //set text
                textview.setText(addressLine);
                //set location to session
                latlng = String.valueOf(gpsTracker.getLatitude()) + "," + String.valueOf(gpsTracker.getLongitude());
                sessionManager.setLocation(addressLine, latlng);
                Toast.makeText(mContext, "onResume , " + latlng, Toast.LENGTH_SHORT).show();
            }
            addData();
        }else {
            gpsTracker.showSettingsAlert();
            mError.setVisibility(View.VISIBLE);
        }

    }

    private void addData() {
        mApiService.getRestoran().enqueue(new Callback<ResponseRestoran>() {
            @Override
            public void onResponse(Call<ResponseRestoran> call, Response<ResponseRestoran> response) {
                String value = response.body().getStatus();
                if(value.equals("1")){
                    data = response.body().getData();
                    adapter = new RestorantAdapter(getActivity(),data);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<ResponseRestoran> call, Throwable t) {
                Toast.makeText(mContext,R.string.lostconnection,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_cart, menu);
    }


//Cart Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        if(item.getItemId()==R.id.cart){
            Intent intent = new Intent(mContext,CartListActivity.class);
            startActivity(intent);

        }

        return true;
    }


}
