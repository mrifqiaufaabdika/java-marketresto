package com.example.abdialam.marketresto.adapter;

import android.app.Activity;
import android.content.Context;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.models.CartList;
import com.example.abdialam.marketresto.models.Menu;
import com.example.abdialam.marketresto.utils.DatabaseHelper;


import java.util.List;

public class CartAdapter extends BaseAdapter {

    Context mContext ;
    List<CartList> dataList;
    DatabaseHelper myDb;
    private ClickListener clickListener;
    ViewHolder viewHolder = null;

    public CartAdapter (Context mContext, List<CartList> data, ClickListener clickListener) {
        super();
        this.dataList = data;
        this.mContext = mContext;
        this.clickListener = clickListener;

    }

    public void removeAt(int position) {
        dataList.remove(position);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.single_row_cart_list,null);

            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            viewHolder.title = (TextView)view.findViewById(R.id.title);
            viewHolder.harga = (TextView)view.findViewById(R.id.harga);
            viewHolder.qty = (TextView)view.findViewById(R.id.qty);
            viewHolder.min = (ImageView) view.findViewById(R.id.min);
            viewHolder.plus = (ImageView) view.findViewById(R.id.plus);
            viewHolder.cross =(ImageView) view.findViewById(R.id.clear);





            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }


        final CartList cart = (CartList)getItem(position);
        viewHolder.imageView.setImageResource(R.drawable.shoppy_logo);
        viewHolder.title.setText(cart.getNama_menu());
        viewHolder.harga.setText(String.valueOf(cart.getHarga()));
        viewHolder.qty.setText(String .valueOf(cart.getQty()));

        viewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener != null){
                    clickListener.itemMin(view,position);
                CartList cl =  dataList.get(position);
                if(cl.getQty()>1) {
                    cl.setQty(cl.getQty() - 1);
                    dataList.set(position, cl);
                    viewHolder.qty.setText(String.valueOf(cl.getQty()));
                    CartAdapter.this.notifyDataSetChanged();
                }
                }

            }
        });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener != null) {
                    clickListener.itemPlus(view,position);
                    CartList cl = dataList.get(position);
                    cl.setQty(cl.getQty() + 1); //incerment item
                    dataList.set(position, cl); //updeting the item list to that new item with incremend quality
                    viewHolder.qty.setText(String.valueOf(cl.getQty()));
                    CartAdapter.this.notifyDataSetChanged();
                }
            }
        });

        viewHolder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(clickListener != null){
                   clickListener.itemDeleted(view,position);
               }
            }
        });

        return view;


    }

    private class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView harga;
        TextView qty;
        ImageView min;
        ImageView plus;
        ImageView cross;

    }


    public interface ClickListener {
        //public void itemClicked(View view, int position);

        public void itemDeleted(View view, int position);

        public void itemPlus(View view,int position);

        public void itemMin (View view,int position);

    }


}
