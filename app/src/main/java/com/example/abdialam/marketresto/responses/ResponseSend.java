package com.example.abdialam.marketresto.responses;

import com.example.abdialam.marketresto.models.Message;
import com.example.abdialam.marketresto.models.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSend {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private Message message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
