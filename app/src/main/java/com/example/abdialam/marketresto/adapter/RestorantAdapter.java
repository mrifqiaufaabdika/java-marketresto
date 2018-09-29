package com.example.abdialam.marketresto.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.abdialam.marketresto.R.color.green;
import static com.example.abdialam.marketresto.R.color.red;

public class RestorantAdapter extends RecyclerView.Adapter<RestorantAdapter.RestoranViewHolder>{

    private List<Restoran> dataList;
    private Context mContext;

    public RestorantAdapter(Context context,List<Restoran> dataList){
        this.dataList =dataList;
        this.mContext = context;
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
        holder.txtTarifDelivery.setText(data.getTarifDelivery());
        holder.txtMinimum.setText(data.getRestoranDeliveryMinimum());
        holder.tvJumlahPesan.setText(data.getJumlahPesan().toString()+ " Pesanan");
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
}
