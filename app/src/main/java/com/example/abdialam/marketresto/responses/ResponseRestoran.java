package com.example.abdialam.marketresto.responses;

import com.example.abdialam.marketresto.models.Restoran;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRestoran {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Restoran> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Restoran> getData() {
        return data;
    }

    public void setData(List<Restoran> data) {
        this.data = data;
    }

}
