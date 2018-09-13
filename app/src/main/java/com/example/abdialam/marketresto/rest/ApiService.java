package com.example.abdialam.marketresto.rest;

import com.example.abdialam.marketresto.responses.ResponseAuth;
import com.example.abdialam.marketresto.responses.ResponseMenu;
import com.example.abdialam.marketresto.responses.ResponseRestoran;
import com.example.abdialam.marketresto.responses.ResponseViewKonsumen;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //fungsi ini untuk memanggil API localhost:8080/


    // Fungsi ini untuk memanggil API 127.0.0.1/auth/signup
    @FormUrlEncoded
    @POST("auth/signup")
    Call<ResponseAuth> signupRequest(@Field("konsumen_nama") String nama,
                                     @Field("konsumen_phone") String phone,
                                     @Field("konsumen_email") String email,
                                     @Field("token") String token,
                                     @Field("tipe") String tipe);


    // Fungsi ini untuk memanggil API 127.0.01/auth/signin

    @GET("auth/signin/{phone}")
    Call<ResponseAuth> signinRequest(@Path("phone") String phone);

//    get konsumen by phone
    @GET("konsumen/{phone}")
    Call<ResponseViewKonsumen> viewKonsumen (@Path("phone") String phone);

    //    get restoran
    @GET("restorant/")
    Call<ResponseRestoran> getRestoran ();

    //    get restoran menu
    @GET("menu/{id_restoran}")
    Call<ResponseMenu> getRestoranMenuById (@Path("id_restoran") String id_restoran);
}
