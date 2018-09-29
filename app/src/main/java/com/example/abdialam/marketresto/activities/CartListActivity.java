package com.example.abdialam.marketresto.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.models.CartList;
import com.example.abdialam.marketresto.models.Restoran;
import com.example.abdialam.marketresto.responses.ResponseRestoran;
import com.example.abdialam.marketresto.responses.ResponseSend;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.utils.DatabaseHelper;
import com.example.abdialam.marketresto.utils.NonScrollListView;
import com.example.abdialam.marketresto.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListActivity extends AppCompatActivity implements CartAdapter.ClickListener{


    private static final String TAG =  "Cart List";
    DatabaseHelper myDb;
    Context mContext;
    SessionManager sessionManager;
    ApiService mApiService ;
    ProgressDialog progressDialog,progressOrder;

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
    HashMap<String,String> user,location;
    String konsumen_id, konsumen_nama,konsumen_phone,id_resto, tempMsgTarifAntar;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartlist);
        mContext = this;
        mApiService = ServerConfig.getAPIService();
        myDb = new DatabaseHelper(mContext);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getUserDetail();
        location = sessionManager.getLocation();
        progressDialog = new ProgressDialog(mContext);



        setListViewHeightBasedOnChildren(list);
        cartList = new ArrayList<CartList>();
        listener =this;
        progressDialog = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);
        getData();
       // getIncomingIntent();





    }

    private void getData (){
        //Mengambil data dari Sqlite
        Cursor res = myDb.getAllCart();
        if(res.getCount() == 0){
            progressDialog.dismiss();
            Toast.makeText(mContext,"anda tidak memiliki cart",Toast.LENGTH_SHORT).show();
            imgEmptyCart.setVisibility(View.VISIBLE);
            scCart.setVisibility(View.GONE);
        }else {
           // res.moveToFirst();//Memulai Cursor pada Posisi Awal
            Toast.makeText(mContext,"anda memiliki cart",Toast.LENGTH_SHORT).show();
            while (res.moveToNext()) {


                Integer id = Integer.valueOf(res.getString(0));
                id_resto = res.getString(1);
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
            get_restoran();


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
        if(qty>1) {
            String id_menu = cart.getId_menu();
            qty -= 1;
            boolean updete = myDb.UpdateCart(qty, id_menu);
            if (updete) {
                SubTotal -= cart.getHarga();
                SetData();
            }
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




//    private void getIncomingIntent (){
//
//        if(getIntent().hasExtra("Resto")){
//            resto = (Restoran)getIntent().getSerializableExtra("Resto");
//        }
//    }

    private double getBiayaAntar (){
        if(resto.getRestoranDelivery().equals("gratis")){
            tempMsgTarifAntar = resto.getRestoranDelivery().toString();
            return 0;
        }else {
            tempMsgTarifAntar = resto.getRestoranDelivery() +" Rp."+resto.getTarifDelivery();
            return Double.valueOf(resto.getTarifDelivery());
        }
    }

    private void SetData(){
        Total = getBiayaAntar()+SubTotal;
        konsumen_id = user.get(SessionManager.ID_USER).toString();
        konsumen_nama =String.valueOf(user.get(SessionManager.NAMA_LENGKAP).toUpperCase());
        konsumen_phone = String.valueOf(user.get(SessionManager.NO_HP));
        mSubTotal.setText("Rp. "+String.valueOf(SubTotal));
        mBiayaAntar.setText(tempMsgTarifAntar);
        mNamaKonsumen.setText(konsumen_nama);
        mPhoneKonsumen.setText("+"+konsumen_phone);
        mTotal.setText("Rp"+String.valueOf(Total));
        mAlamatAntar.setText(location.get(SessionManager.ALAMAT));

    }


    public void get_restoran (){
       mApiService.getRestoranByID(id_resto).enqueue(new Callback<ResponseRestoran>() {
           @Override
           public void onResponse(Call<ResponseRestoran> call, Response<ResponseRestoran> response) {
               //    get restoran
               progressDialog.dismiss();
               if(response.isSuccessful()){

                   Toast.makeText(mContext,"suskses dapat resto",Toast.LENGTH_SHORT).show();
                    resto = response.body().getData().get(0);
                    SetData();
               }
           }

           @Override
           public void onFailure(Call<ResponseRestoran> call, Throwable t) {
               progressDialog.dismiss();
                Toast.makeText(mContext,R.string.lostconnection,Toast.LENGTH_SHORT).show();
           }
       });

    }




    @OnClick(R.id.btnOrder) void order (){
        Toast.makeText(mContext,"Sub total ="+String.valueOf(SubTotal)+"Total = "+String.valueOf(Total),Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Konfirmasi Pesanan");
        builder.setMessage("Pesanan anda akan diterima "+resto.getRestoranNama().toString().toUpperCase()+" ,total pembayaran Rp."+String.valueOf(Total));
        builder.setCancelable(false);
        builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //send data to server
                progressOrder = new ProgressDialog(mContext);
                progressOrder = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);
                sendOrder();
            }
        });
        builder.setNegativeButton("Cek Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        final AlertDialog alert =builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
            }
        });
        alert.show();


    }

    private void sendOrder() {

        String title = konsumen_nama;
        String message = "";
        String restoran_phone = resto.getRestoranPemilikPhone().toString();
        String konsumen_id =user.get(SessionManager.ID_USER).toString();
        final String restoran_id = id_resto;
        String pesan_lokasi = location.get(SessionManager.LATLANG);
        String pesan_alamat = "Jl. Srikandi Perumahan Wadya Graha 1 Pekanbaru";
        String pesan_catatan = mCatatanAlamat.getText().toString();
        String jarak_antar ="200 km";
        String pesan_biaya_antar =Double.toString(getBiayaAntar());
        String pesan_status="proses";
        String pesan_metode_bayar ="cash";

        mApiService.order(title,message,restoran_phone,konsumen_id,restoran_id,
                pesan_lokasi,pesan_alamat,pesan_catatan,jarak_antar,
                pesan_biaya_antar,pesan_status,pesan_metode_bayar).enqueue(new Callback<ResponseSend>() {
            @Override
            public void onResponse(Call<ResponseSend> call, Response<ResponseSend> response) {
                progressOrder.dismiss();
                if(response.isSuccessful()){
                    String success = response.body().getMessage().getSuccess().toString();
                    String id = response.body().getId();

                    if(success.equals("1")){
                        Toast.makeText(mContext,"Berhasil Mengirim Pesanan " + id,Toast.LENGTH_SHORT).show();

                        setDetailPesanan(id);
                    }else if (success.equals("0")){
                        Toast.makeText(mContext,(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(mContext,getString(R.string.error),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSend> call, Throwable t) {
                progressOrder.dismiss();
                    Toast.makeText(mContext,R.string.lostconnection,Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    private void setDetailPesanan(String id) {
        for (int i = 0; i < cartList.size(); i++) {
            String menu_id =cartList.get(i).getId_menu().toString();
            String harga   = String.valueOf(cartList.get(i).getHarga());
            String qty = cartList.get(i).getQty().toString();
            String catatan = cartList.get(i).getCatatan();

            mApiService.setPesanDetail(id,menu_id,harga,qty,catatan).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(mContext,"sucses",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
        //kosongkan cart dari db
        myDb.deleteAll();

    }


}

