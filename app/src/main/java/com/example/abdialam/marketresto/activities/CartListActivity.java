package com.example.abdialam.marketresto.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.adapter.CartAdapter;
import com.example.abdialam.marketresto.models.CartList;
import com.example.abdialam.marketresto.models.Restoran;
import com.example.abdialam.marketresto.utils.DatabaseHelper;
import com.example.abdialam.marketresto.utils.NonScrollListView;
import com.example.abdialam.marketresto.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListActivity extends AppCompatActivity implements CartAdapter.ClickListener{



    DatabaseHelper myDb;
    Context mContext;
    SessionManager sessionManager;


    private ArrayList<CartList> cartList;
    CartAdapter.ClickListener listener;
    private CartAdapter adapter;
    Restoran resto;

    @BindView(R.id.listview) NonScrollListView list;
    @BindView(R.id.imgEmptyCart) ImageView imgEmptyCart;
    @BindView(R.id.scCart) ScrollView scCart;
    @BindView(R.id.tvSubTotal) TextView mSubTotal;
    @BindView(R.id.tvBiayaAntar) TextView mBiayaAntar;
    @BindView(R.id.tvTotal) TextView mTotal;
    @BindView(R.id.tvNamaKonsumen) TextView mNamaKonsumen;
    @BindView(R.id.tvPhoneKonsumen) TextView mPhoneKonsumen;
    @BindView(R.id.tvAlamatAntar) TextView mAlamatAntar;
    @BindView(R.id.etCatatanAlamat)
    EditText mCatatanAlamat;
    @BindView(R.id.btnOrder) TextView btnOrder;
    double SubTotal,Total ;
    HashMap<String,String> user;
    String konsumen_id, konsumen_nama,konsumen_phone;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartlist);
        mContext = this;
        myDb = new DatabaseHelper(mContext);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getUserDetail();



        setListViewHeightBasedOnChildren(list);
        cartList = new ArrayList<CartList>();
        listener =this;

        getData();
        getIncomingIntent();
        SetData();
        SendData();


    }

    private void getData (){
        //Mengambil data dari Sqlite
        Cursor res = myDb.getAllCart();
        if(res.getCount() == 0){
            Toast.makeText(mContext,"anda tidak memiliki cart",Toast.LENGTH_SHORT).show();
            imgEmptyCart.setVisibility(View.VISIBLE);
            scCart.setVisibility(View.GONE);
        }else {
           // res.moveToFirst();//Memulai Cursor pada Posisi Awal
            Toast.makeText(mContext,"anda memiliki cart",Toast.LENGTH_SHORT).show();
            while (res.moveToNext()) {


                Integer id = Integer.valueOf(res.getString(0));
                String id_resto = res.getString(1);
                String id_menu = res.getString(2);
                double harga = Double.valueOf(res.getString(3));
                Integer qty = Integer.valueOf(res.getString(4));
                String catatan =res.getString(5);
                String nama_resto = res.getString(6);

                SubTotal += (harga * qty);

                CartList temp = new CartList(id,id_resto,id_menu,harga,qty,catatan,nama_resto);
                cartList.add(temp);
            }
            adapter = new CartAdapter(CartListActivity.this,cartList,listener);
            list.setAdapter(adapter);

        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void itemDeleted(View view, int position) {
        CartList cart = (CartList) cartList.get(position);
        SubTotal = SubTotal - (cart.getHarga() * cart.getQty());
        SetData();
        int msg = myDb.deleteCart(cart.getId());
        adapter.removeAt(position);

        if(cartList.size() ==0){
            //set error untuk melihat pesan kosong
//            emtyFavorites.setVisibility(View.VISIBLE);
            Toast.makeText(mContext,"data kosong",Toast.LENGTH_SHORT).show();
            imgEmptyCart.setVisibility(View.VISIBLE);
            scCart.setVisibility(View.GONE);
        }
    }



    @Override
    public void itemMin(View view, int position) {
        CartList cart = (CartList) cartList.get(position);
        int qty = cart.getQty();
        String id_menu = cart.getId_menu();
        qty -= 1;
        boolean updete = myDb.UpdateCart(qty,id_menu);
        if(updete){
            SubTotal -= cart.getHarga();
            SetData();
        }

    }

    @Override
    public void itemPlus(View view, int position) {
        CartList cart = (CartList) cartList.get(position);
        int qty = cart.getQty();
        String id_menu = cart.getId_menu();
        qty += 1;
        boolean updete = myDb.UpdateCart(qty,id_menu);
        if(updete){
            SubTotal += cart.getHarga();
            SetData();
        }

    }




    private void getIncomingIntent (){

        if(getIntent().hasExtra("Resto")){
            resto = (Restoran)getIntent().getSerializableExtra("Resto");
        }
    }

    private double getBiayaAntar (){
        if(resto.getRestoranDelivery().equals("gratis")){
            return 0;
        }else {
            return Double.valueOf(resto.getTarifDelivery());
        }
    }

    private void SetData(){
        Total = getBiayaAntar()+SubTotal;
        konsumen_id = user.get(SessionManager.ID_USER).toString();
        konsumen_nama =String.valueOf(user.get(SessionManager.NAMA_LENGKAP).toUpperCase());
        konsumen_phone = String.valueOf(user.get(SessionManager.NO_HP));

        mSubTotal.setText("Rp. "+String.valueOf(SubTotal));
        mBiayaAntar.setText(resto.getRestoranDelivery());
        mNamaKonsumen.setText(konsumen_nama);
        mPhoneKonsumen.setText("+"+konsumen_phone);
        mTotal.setText("Rp"+String.valueOf(Total));

    }


    public void SendData(){
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"SubTotal = "+String.valueOf(SubTotal),Toast.LENGTH_SHORT).show();
            }
        });
    }


}

