package com.example.abdialam.marketresto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartList {

    //private int image;
    private Integer id;
    private String id_resto;
    private String id_menu;
    private String harga;
    private Integer qty;
    private String catatan;
    private String nama_menu;


    public CartList(int id, String id_resto, String id_menu,String harga,Integer qty, String catatan,String nama_menu) {
       this.id = id;
       this.id_resto = id_resto;
       this.id_menu = id_menu;
       this.harga = harga;
       this.qty = qty;
       this.catatan = catatan;
       this.nama_menu = nama_menu;
    }

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }





    public Integer getId() {
        return id;
    }

    public void setPrice(Integer id) {
        this.id = id;
    }


    public String getId_resto() {
        return id_resto;
    }

    public void setId_resto(String id_resto) {
        this.id_resto = id_resto;
    }


    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_resto) {
        this.nama_menu = nama_resto;
    }
}
