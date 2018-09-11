package com.example.abdialam.marketresto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id_konsumen")
    @Expose
    private Integer idKonsumen;
    @SerializedName("konsumen_phone")
    @Expose
    private String konsumenPhone;
    @SerializedName("konsumen_nama")
    @Expose
    private String konsumenNama;
    @SerializedName("konsumen_email")
    @Expose
    private String konsumenEmail;
    @SerializedName("konsumen_credit_balance")
    @Expose
    private Integer konsumenCreditBalance;
    @SerializedName("konsumen_baru")
    @Expose
    private Integer konsumenBaru;
    @SerializedName("blacklist")
    @Expose
    private Integer blacklist;
    @SerializedName("konsumen_create")
    @Expose
    private String konsumenCreate;
    @SerializedName("id_pengguna")
    @Expose
    private Integer idPengguna;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("tipe")
    @Expose
    private String tipe;

    public Integer getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(Integer idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getKonsumenPhone() {
        return konsumenPhone;
    }

    public void setKonsumenPhone(String konsumenPhone) {
        this.konsumenPhone = konsumenPhone;
    }

    public String getKonsumenNama() {
        return konsumenNama;
    }

    public void setKonsumenNama(String konsumenNama) {
        this.konsumenNama = konsumenNama;
    }

    public String getKonsumenEmail() {
        return konsumenEmail;
    }

    public void setKonsumenEmail(String konsumenEmail) {
        this.konsumenEmail = konsumenEmail;
    }

    public Integer getKonsumenCreditBalance() {
        return konsumenCreditBalance;
    }

    public void setKonsumenCreditBalance(Integer konsumenCreditBalance) {
        this.konsumenCreditBalance = konsumenCreditBalance;
    }

    public Integer getKonsumenBaru() {
        return konsumenBaru;
    }

    public void setKonsumenBaru(Integer konsumenBaru) {
        this.konsumenBaru = konsumenBaru;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public String getKonsumenCreate() {
        return konsumenCreate;
    }

    public void setKonsumenCreate(String konsumenCreate) {
        this.konsumenCreate = konsumenCreate;
    }

    public Integer getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(Integer idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
