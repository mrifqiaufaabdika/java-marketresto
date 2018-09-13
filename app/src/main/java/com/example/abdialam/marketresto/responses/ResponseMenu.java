package com.example.abdialam.marketresto.responses;

import com.example.abdialam.marketresto.models.Menu;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMenu {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("data")
    @Expose
    private List<Menu> data = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Menu> getData() {
        return data;
    }

    public void setData(List<Menu> data) {
        this.data = data;
    }
}
