package com.example.abdialam.marketresto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Kategori implements Serializable {
    @SerializedName("menu_kategori_id")
    @Expose
    private Integer menuKategoriId;
    @SerializedName("kategori_nama")
    @Expose
    private String kategoriNama;
    @SerializedName("kategori_deskripsi")
    @Expose
    private String kategoriDeskripsi;
    @SerializedName("jumlah_menu")
    @Expose
    private Integer jumlahMenu;

    public Integer getMenuKategoriId() {
        return menuKategoriId;
    }

    public void setMenuKategoriId(Integer menuKategoriId) {
        this.menuKategoriId = menuKategoriId;
    }

    public String getKategoriNama() {
        return kategoriNama;
    }

    public void setKategoriNama(String kategoriNama) {
        this.kategoriNama = kategoriNama;
    }

    public String getKategoriDeskripsi() {
        return kategoriDeskripsi;
    }

    public void setKategoriDeskripsi(String kategoriDeskripsi) {
        this.kategoriDeskripsi = kategoriDeskripsi;
    }

    public Integer getJumlahMenu() {
        return jumlahMenu;
    }

    public void setJumlahMenu(Integer jumlahMenu) {
        this.jumlahMenu = jumlahMenu;
    }
}
