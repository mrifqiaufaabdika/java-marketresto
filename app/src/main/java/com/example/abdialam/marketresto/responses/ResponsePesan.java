package com.example.abdialam.marketresto.responses;

import com.example.abdialam.marketresto.models.Pesan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePesan {
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("messge")
    @Expose
    private String messge;
    @SerializedName("pesan")
    @Expose
    private List<Pesan> pesan = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public List<Pesan> getPesan() {
        return pesan;
    }

    public void setPesan(List<Pesan> pesan) {
        this.pesan = pesan;
    }

}
