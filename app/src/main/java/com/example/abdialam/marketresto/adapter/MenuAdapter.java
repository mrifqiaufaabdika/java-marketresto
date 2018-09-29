package com.example.abdialam.marketresto.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.models.Menu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private Context mContext;
    FragmentManager fragmentManager;
    private OnItemClickListener listener;





    public MenuAdapter(Context mContext, List<Menu> data, OnItemClickListener listener) {
        super();
        this.menuList = data;
        this.mContext = mContext;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list_menu, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Menu data = menuList.get(position);

        holder.mNamaMenu.setText(data.getMenuNama());
        holder.mHargaMenu.setText(data.getMenuHarga());
        holder.mParentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext,"love",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        oprasional(holder,data.getMenuKetersedian());



        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
     //           Toast.makeText(mContext,"you click "+ data.getMenuNama(),Toast.LENGTH_SHORT).show();
                //Menu Tersedia
                if(data.getMenuKetersedian() == 1) {

                    listener.onItemCliked(view, position);
                }else {
                    Toast.makeText(mContext,data.getMenuNama() +" Tidak Tesedia",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNamaMenu)
        TextView mNamaMenu;
        @BindView(R.id.tvHargaMenu)
        TextView mHargaMenu;
        @BindView(R.id.parentLayout)
        RelativeLayout mParentLayout;
        @BindView(R.id.tvKetersedian)
        TextView mKetersediaan;

        public MyViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemCliked(View v,int position);
    }

    public void oprasional (MyViewHolder holder, Integer code){

        if (code == 1){
            holder.mKetersediaan.setText("Tersedia");
            holder.mKetersediaan.setTextColor(ContextCompat.getColor(mContext,R.color.green));
        } else {
            holder.mKetersediaan.setText("Tidak Tersedia");
            holder.mKetersediaan.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        }
    }

}


