package com.example.abdialam.marketresto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pesan {
    @SerializedName("id_pesan")
    @Expose
    private String idPesan;
    @SerializedName("konsumen_id")
    @Expose
    private Integer konsumenId;
    @SerializedName("restoran_id")
    @Expose
    private Integer restoranId;
    @SerializedName("pesan_waktu")
    @Expose
    private String pesanWaktu;
    @SerializedName("pesan_lokasi")
    @Expose
    private String pesanLokasi;
    @SerializedName("pesan_alamat")
    @Expose
    private String pesanAlamat;
    @SerializedName("pesan_catatan")
    @Expose
    private String pesanCatatan;
    @SerializedName("pesan_metode_bayar")
    @Expose
    private String pesanMetodeBayar;
    @SerializedName("jarak_antar")
    @Expose
    private Integer jarakAntar;
    @SerializedName("pesan_biaya_antar")
    @Expose
    private Integer pesanBiayaAntar;
    @SerializedName("pesan_status")
    @Expose
    private String pesanStatus;
    @SerializedName("id_restoran")
    @Expose
    private Integer idRestoran;
    @SerializedName("restoran_nama")
    @Expose
    private String restoranNama;
    @SerializedName("restoran_phone")
    @Expose
    private String restoranPhone;
    @SerializedName("detailpesan")
    @Expose
    private List<Detailpesan> detailpesan = null;

    public String getIdPesan() {
        return idPesan;
    }

    public void setIdPesan(String idPesan) {
        this.idPesan = idPesan;
    }

    public Integer getKonsumenId() {
        return konsumenId;
    }

    public void setKonsumenId(Integer konsumenId) {
        this.konsumenId = konsumenId;
    }

    public Integer getRestoranId() {
        return restoranId;
    }

    public void setRestoranId(Integer restoranId) {
        this.restoranId = restoranId;
    }

    public String getPesanWaktu() {
        return pesanWaktu;
    }

    public void setPesanWaktu(String pesanWaktu) {
        this.pesanWaktu = pesanWaktu;
    }

    public String getPesanLokasi() {
        return pesanLokasi;
    }

    public void setPesanLokasi(String pesanLokasi) {
        this.pesanLokasi = pesanLokasi;
    }

    public String getPesanAlamat() {
        return pesanAlamat;
    }

    public void setPesanAlamat(String pesanAlamat) {
        this.pesanAlamat = pesanAlamat;
    }

    public String getPesanCatatan() {
        return pesanCatatan;
    }

    public void setPesanCatatan(String pesanCatatan) {
        this.pesanCatatan = pesanCatatan;
    }

    public String getPesanMetodeBayar() {
        return pesanMetodeBayar;
    }

    public void setPesanMetodeBayar(String pesanMetodeBayar) {
        this.pesanMetodeBayar = pesanMetodeBayar;
    }

    public Integer getJarakAntar() {
        return jarakAntar;
    }

    public void setJarakAntar(Integer jarakAntar) {
        this.jarakAntar = jarakAntar;
    }

    public Integer getPesanBiayaAntar() {
        return pesanBiayaAntar;
    }

    public void setPesanBiayaAntar(Integer pesanBiayaAntar) {
        this.pesanBiayaAntar = pesanBiayaAntar;
    }

    public String getPesanStatus() {
        return pesanStatus;
    }

    public void setPesanStatus(String pesanStatus) {
        this.pesanStatus = pesanStatus;
    }

    public Integer getIdRestoran() {
        return idRestoran;
    }

    public void setIdRestoran(Integer idRestoran) {
        this.idRestoran = idRestoran;
    }

    public String getRestoranNama() {
        return restoranNama;
    }

    public void setRestoranNama(String restoranNama) {
        this.restoranNama = restoranNama;
    }

    public String getRestoranPhone() {
        return restoranPhone;
    }

    public void setRestoranPhone(String restoranPhone) {
        this.restoranPhone = restoranPhone;
    }

    public List<Detailpesan> getDetailpesan() {
        return detailpesan;
    }

    public void setDetailpesan(List<Detailpesan> detailpesan) {
        this.detailpesan = detailpesan;
    }
}