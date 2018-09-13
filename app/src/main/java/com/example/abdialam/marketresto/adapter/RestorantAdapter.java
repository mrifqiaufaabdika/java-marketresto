package com.example.abdialam.marketresto.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.activities.RestoMenuActivity;
import com.example.abdialam.marketresto.models.Restoran;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestorantAdapter extends RecyclerView.Adapter<RestorantAdapter.RestoranViewHolder>{

    private List<Restoran> dataList;
    private Context mContext;

    public RestorantAdapter(Context context,List<Restoran> dataList){
        this.dataList =dataList;
        this.mContext = context;
    }

    @Override
    public RestoranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_restoran,parent,false);
        RestoranViewHolder holder = new RestoranViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RestoranViewHolder holder, int position) {
        final Restoran data = dataList.get(position);
        holder.txtNamaResto.setText(data.getRestoranNama());
        holder.txtTarifDelivery.setText(data.getTarifDelivery());
        holder.txtMinimum.setText(data.getRestoranDeliveryMinimum());

        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Anda Memilih "+ data.getRestoranNama(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, RestoMenuActivity.class);
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
        @BindView(R.id.parentLayout)
        RelativeLayout mParentLayout;


        public RestoranViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
