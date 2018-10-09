package com.example.abdialam.marketresto.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.models.Favorit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.MyViewHolder> {

    private List<Favorit> favoritList ;
    private Context mContext;

    public FavoritAdapter (Context context, List<Favorit> favorits){
        this.mContext = context;
        this.favoritList = favorits;
    }


    @Override
    public FavoritAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list_favorit, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoritAdapter.MyViewHolder holder, int position) {
        final Favorit favorit = favoritList.get(position);
        holder.mNamaMenu.setText(favorit.getMenuNama());
        holder.mNamaResto.setText(favorit.getRestoranNama());
        holder.mHargaMenu.setText(favorit.getMenuHarga());
    }

    @Override
    public int getItemCount() {
        return favoritList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNamaMenu)
        TextView mNamaMenu;
        @BindView(R.id.tvNamaResto)
        TextView mNamaResto;
        @BindView(R.id.tvHargaMenu)
        TextView mHargaMenu;
        @BindView(R.id.imgDelete)
        ImageView mDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
