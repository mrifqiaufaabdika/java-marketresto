package com.example.abdialam.marketresto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.models.Menu;
import com.example.abdialam.marketresto.models.Restoran;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<Menu> dataList ;
    private Context mContext;

    public MenuAdapter (Context context, List<Menu> data){
        this.mContext = context;
        this.dataList = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_menu,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Menu data = dataList.get(position);
        holder.mNamaMenu.setText(data.getMenuNama());
        holder.mHargaMenu.setText(data.getMenuHarga());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNamaMenu)
        TextView mNamaMenu;
        @BindView(R.id.tvHargaMenu)
        TextView mHargaMenu;


        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
