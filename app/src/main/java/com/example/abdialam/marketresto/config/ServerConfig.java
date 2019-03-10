package com.example.abdialam.marketresto.config;

import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.rest.ClientService;

public class ServerConfig {
    // .0.2.2 ini adalah localhost.
    //192.168.100.5
    //192.168.43.210
    //192.168.1.193

    //https://marketresto.000webhostapp.com/public/
    //https://topapp.id/marketresto/api/v1/

    //private static final String BASE_URL_API = "http://192.168.100.5/marketresto/api/v1/";
    private static final String BASE_URL_API = "https://topapp.id/marketresto/api/v1/";
    // Mendeklarasikan Interface BaseApiService
    public static ApiService getAPIService(){
        return ClientService.getClient(BASE_URL_API).create(ApiService.class);
    }
}
