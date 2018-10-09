package com.example.abdialam.marketresto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorit {
    @SerializedName("id_favorite")
    @Expose
    private Integer idFavorite;
    @SerializedName("konsumen_id")
    @Expose
    private Integer konsumenId;
    @SerializedName("menu_id")
    @Expose
    private Integer menuId;
    @SerializedName("menu_nama")
    @Expose
    private String menuNama;
    @SerializedName("menu_harga")
    @Expose
    private String menuHarga;
    @SerializedName("restoran_nama")
    @Expose
    private String restoranNama;

    public Integer getIdFavorite() {
        return idFavorite;
    }

    public void setIdFavorite(Integer idFavorite) {
        this.idFavorite = idFavorite;
    }

    public Integer getKonsumenId() {
        return konsumenId;
    }

    public void setKonsumenId(Integer konsumenId) {
        this.konsumenId = konsumenId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuNama() {
        return menuNama;
    }

    public void setMenuNama(String menuNama) {
        this.menuNama = menuNama;
    }

    public String getMenuHarga() {
        return menuHarga;
    }

    public void setMenuHarga(String menuHarga) {
        this.menuHarga = menuHarga;
    }

    public String getRestoranNama() {
        return restoranNama;
    }

    public void setRestoranNama(String restoranNama) {
        this.restoranNama = restoranNama;
    }
}
