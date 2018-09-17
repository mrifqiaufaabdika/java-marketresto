package com.example.abdialam.marketresto.fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.models.Menu;
import com.example.abdialam.marketresto.utils.DatabaseHelper;

public class DialogPlaceOrderFragment extends DialogFragment{

    public static final String ARG_ITEM_ID = "custom_dialog_fragment";
    Menu menuItems;
    TextView item_title,mDeskripsi,mJumlah,mTotal,mMin,mUp,mHarga;
    int qty;
    double harga,total_harga;
    String id_restoran, id_menu,catatan,nama_menu;
    DatabaseHelper myDb;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        menuItems = (Menu) bundle.getSerializable("selectedItem");

        myDb = new DatabaseHelper(getActivity());

        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //set the layout for the dialog
        dialog.setContentView(R.layout.dialog_place_order);
        dialog.setCancelable(true);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        item_title = (TextView)  dialog.findViewById(R.id.item_title);
        mDeskripsi = (TextView) dialog.findViewById(R.id.description_title);
        mHarga =(TextView) dialog.findViewById(R.id.item_description);
        mJumlah = (TextView) dialog.findViewById(R.id.tvQty);
        mMin = (TextView) dialog.findViewById(R.id.tvMin);
        mUp = (TextView) dialog.findViewById(R.id.tvUp);
        mTotal = (TextView) dialog.findViewById(R.id.total_price);

        qty = 1; total_harga = Double.valueOf(menuItems.getMenuHarga());

        setData();

        mJumlah.setText(String.valueOf(qty));
        mTotal.setText(String.valueOf(total_harga));



        mUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                total_harga += Double.valueOf(menuItems.getMenuHarga());
                mJumlah.setText(String.valueOf(qty));
                mTotal.setText(String.valueOf(total_harga));


            }
        });

        mMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty--;
                total_harga -=  Double.valueOf(menuItems.getMenuHarga());
                mJumlah.setText(String.valueOf(qty));
                mTotal.setText(String.valueOf(total_harga));
            }
        });

        dialog.findViewById(R.id.order_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ambil Seluruh data di db
                Cursor res = myDb.getAllCart();
                //cek berisi atau tidak
                //jika tidak berisi
                if(res.getCount()==0) {
                    // Toast.makeText(getActivity(),"jumlah "+ qty +",harga "+String.valueOf(total_harga) ,Toast.LENGTH_SHORT).show();
                    //Insert Data
                    insertData();
                //jika bersisi
                }else {
                    res.moveToFirst();
                    //cek apakah dipilih dari restoran yang sama ?
                    //Jika dari restoran yang sama
                    if(res.getString(1).equals(id_restoran)){
                        //Toast.makeText(getActivity(),"data sesuai restoran",Toast.LENGTH_SHORT).show();

                        //ambil di db apakah menu sudah ada
                        Cursor cekUpdate = myDb.getByID(id_menu);
                        //Toast.makeText(getActivity(),String.valueOf(cekUpdate.getCount()),Toast.LENGTH_SHORT).show();
                        //Jika belum Insert data
                        if(cekUpdate.getCount()==0){
                            insertData();
                            //Toast.makeText(getActivity(),"Data Masuk !jml data"+String.valueOf(cekUpdate.getCount())+"menu id"+ id_menu,Toast.LENGTH_SHORT).show();
                        //Jika Ada Update data
                        }else {
                            //Toast.makeText(getActivity(),"Data Update !jml data"+String.valueOf(cekUpdate.getCount())+"menu id"+ id_menu,Toast.LENGTH_SHORT).show();
                            cekUpdate.moveToFirst();
                            int qtySkg = Integer.valueOf(cekUpdate.getString(4));
                            int qtyBaru = qtySkg+qty;
                            boolean update = myDb.UpdateCart(qtyBaru,id_menu);
                            dismiss();
                        }
                    //Jika beda restoran
                    }else {
                        Toast.makeText(getActivity(),"Opps, Keranjang Hanya untuk satu restoran",Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }
        });


        return dialog;

    }

    private void setData(){
        id_restoran = menuItems.getMenuRestoranId().toString();
        id_menu = menuItems.getIdMenu().toString();
        catatan = "a";
        harga = Double.valueOf(menuItems.getMenuHarga());
        nama_menu = menuItems.getMenuNama().toString();


        item_title.setText(menuItems.getMenuNama().toString());
        mDeskripsi.setText(menuItems.getMenuDeskripsi().toString());
        mHarga.setText(menuItems.getMenuHarga().toString());
    }

    public void insertData(){
        boolean isInserted = myDb.insertDataCart(id_restoran, id_menu, total_harga, qty, catatan, nama_menu);
        if (isInserted = true) {
            Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getActivity(), "Data not Inserted", Toast.LENGTH_LONG).show();
        }
        dismiss();
    }


}
