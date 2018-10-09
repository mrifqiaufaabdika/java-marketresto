package com.example.abdialam.marketresto.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.utils.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng location ;
    private int geocoderMaxResult = 1;
    Context mContext;
    Marker marker;
    String alamat ;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sessionManager = new SessionManager(mContext);
        LatLng defaut = new LatLng(0.513790, 101.443923);

        //jika dapat lokasi dari resto fragment
        if(getIntent().hasExtra("location")){
            String loc = getIntent().getStringExtra("location");
            String[] latlong = loc.split(",");
            double lat = Double.parseDouble(latlong[0]);
            double lng = Double.parseDouble(latlong[1]);
            LatLng a = new LatLng(lat,lng);
            location = a;
        }else {
            location = defaut;
        }
        alamat = getAddressLine(mContext);

        Button SetLocation = (Button) findViewById(R.id.btnSetLocation);

        //Click set Lokasi
        SetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strLatlng = String.valueOf(location.latitude)+","+String.valueOf(location.longitude);
                sessionManager.setLocation(alamat,strLatlng);
                Intent intent = new Intent(MapsActivity.this,MainActivity.class);
                intent.putExtra("aksi",alamat);
                startActivity(intent);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


        marker = mMap.addMarker(new MarkerOptions().position(location).title("Lokasi : ").snippet(alamat).draggable(true));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,17),5000,null);
        mMap.setMyLocationEnabled(true);
        marker.showInfoWindow();



        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Location currentLoc = mMap.getMyLocation();
                LatLng temp = new LatLng(currentLoc.getLatitude(),currentLoc.getLongitude());
                Toast.makeText(mContext,"Loc "+location,Toast.LENGTH_SHORT).show();
                location = temp;
                marker.setPosition(location);
                alamat = getAddressLine(mContext).toString();
                marker.setSnippet(alamat);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,17),5000,null);
                marker.showInfoWindow();
                return true;
            }
        });




        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marke) {

            }

            @Override
            public void onMarkerDragEnd(Marker marke) {
                location = marke.getPosition();
                alamat = getAddressLine(mContext).toString();
                marker.setSnippet(alamat);
                marker.showInfoWindow();

            }
        });
    }





    public List<Address> getGeocoderAddress(Context context){
        if(location != null){
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                List<Address> addresses = geocoder.getFromLocation(location.latitude,location.longitude,geocoderMaxResult);
                return addresses;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getAddressLine (Context context){
        List<Address> addresses = getGeocoderAddress(context);

        if(addresses != null && addresses.size() > 0){
            Address address = addresses.get(0);
            String addresLine = address.getAddressLine(0);
            return addresLine;
        }else {
            return "not faoud" ;
        }
    }


}
