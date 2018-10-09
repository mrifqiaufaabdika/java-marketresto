package com.example.abdialam.marketresto.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.activities.MenuActivity;
import com.example.abdialam.marketresto.models.Restoran;
import com.example.abdialam.marketresto.utils.SessionManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.abdialam.marketresto.R.color.accent_material_light;
import static com.example.abdialam.marketresto.R.color.background_floating_material_dark;
import static com.example.abdialam.marketresto.R.color.green;
import static com.example.abdialam.marketresto.R.color.red;

public class RestorantAdapter extends RecyclerView.Adapter<RestorantAdapter.RestoranViewHolder>{

    private List<Restoran> dataList;
    private Context mContext;
    SessionManager sessionManager;
    HashMap<String,String> loca;
    String strDistance;


    public RestorantAdapter(Context context,List<Restoran> dataList){
        this.dataList =dataList;
        this.mContext = context;
        sessionManager = new SessionManager(mContext);
        loca = sessionManager.getLocation();
    }

    @Override
    public RestoranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list_restoran,parent,false);
        RestoranViewHolder holder = new RestoranViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(RestoranViewHolder holder, final int position) {
        final Restoran data = dataList.get(position);
        holder.txtNamaResto.setText(data.getRestoranNama());
        holder.txtTarifDelivery.setText(kursIndonesia(Double.parseDouble(data.getTarifDelivery())));
        holder.txtMinimum.setText(kursIndonesia(Double.parseDouble(data.getRestoranDeliveryMinimum())));
        holder.tvJumlahPesan.setText(data.getJumlahPesan().toString()+ " Pesanan");
        String Deskripsi = data.getRestoranDeskripsi().substring(0,1).toUpperCase() + data.getRestoranDeskripsi().substring(1);
        holder.tvDeskripsi.setText(Deskripsi);
        hitung_jarak(data.getRestoranLokasi());
        holder.tvJarak.setText(strDistance);

        oprasional(holder,data.getRestoranOperasional());



        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Anda Memilih "+ data.getRestoranNama(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.putExtra("Resto",  data);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class RestoranViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvNamaResto) TextView txtNamaResto;
        @BindView(R.id.tvTarif) TextView txtTarifDelivery;
        @BindView(R.id.tvMin) TextView txtMinimum;
        @BindView(R.id.parentLayout) LinearLayout mParentLayout;
        @BindView(R.id.tvJumlah_pesan) TextView tvJumlahPesan;
        @BindView(R.id.tvOprasional) TextView tvOptasional;
        @BindView(R.id.tvDeskripsi_resto) TextView tvDeskripsi;
        @BindView(R.id.tvJarak) TextView tvJarak;




        public RestoranViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


    public void oprasional (RestoranViewHolder holder, Integer code){

        if (code == 1){
            holder.tvOptasional.setText("Buka");
            holder.tvOptasional.setTextColor(ContextCompat.getColor(mContext,R.color.green));
        } else {
            holder.tvOptasional.setText("Tutup");
            holder.tvOptasional.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        }
    }

    public void hitung_jarak(String lokasi_resto){
        String [] lokasi =loca.get(SessionManager.LATLANG).split(",");
        String [] locResto = lokasi_resto.split(",");
        double lat1 = Double.parseDouble(lokasi[0]);
        double lng1 = Double.parseDouble(lokasi[1]);
        double lat2 = Double.parseDouble(locResto[0]);
        double lng2 = Double.parseDouble(locResto[1]);
        Location asal =  new Location("point A");
        asal.setLatitude(lat1);
        asal.setLongitude(lng1);
        Location tujuan =  new Location("point B");
        tujuan.setLatitude(lat2);
        tujuan.setLongitude(lng2);

        double distance = (double) Math.floor(asal.distanceTo(tujuan)/1000 *100)/100;

        if(distance < 1){
            strDistance = String.valueOf(distance*1000) + " m";
        }else {
            strDistance = String.valueOf(distance) + " km";
        }


    }

    public String kursIndonesia(double nominal){
        Locale localeID = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String idnNominal = formatRupiah.format(nominal);
        return idnNominal;


    }




}
