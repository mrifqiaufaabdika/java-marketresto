package com.example.abdialam.marketresto.rest;

import com.example.abdialam.marketresto.responses.ResponseAuth;
import com.example.abdialam.marketresto.responses.ResponseMenu;
import com.example.abdialam.marketresto.responses.ResponseRestoran;
import com.example.abdialam.marketresto.responses.ResponseSend;
import com.example.abdialam.marketresto.responses.ResponseViewKonsumen;

import okhttp3.ResponseBody;
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
    @GET("restoran/")
    Call<ResponseRestoran> getRestoran ();

    //    get restoran
    @GET("restoran/{id_restoran}")
    Call<ResponseRestoran> getRestoranByID (@Path("id_restoran") String id_restoran);

    //    get restoran menu
    @GET("menu/{id_restoran}")
    Call<ResponseMenu> getRestoranMenuById (@Path("id_restoran") String id_restoran);


    // OrderPesanan
    @FormUrlEncoded
    @POST("send/")
    Call<ResponseSend> order(@Field("title") String title,
                             @Field("message") String message,
                             @Field("restoran_phone") String restoran_phone,
                             @Field("konsumen_id") String konsumen_id,
                             @Field("restoran_id") String restoran_id,
                             @Field("pesan_lokasi") String pesan_lokasi,
                             @Field("pesan_alamat")String pesan_alamat,
                             @Field("pesan_catatan") String pesan_catatan,
                             @Field("jarak_antar") String jarak_antar,
                             @Field("pesan_biaya_antar")String pesan_biaya_antar,
                             @Field("pesan_status")String pesan_status,
                             @Field("pesan_metode_bayar") String pesan_metode_bayar);

    @FormUrlEncoded
    @POST("pesandetail/")
    Call<ResponseBody> setPesanDetail (@Field("pesan_id") String pesan_id,
                                       @Field("menu_id") String menu_id,
                                       @Field("harga") String harga,
                                       @Field("qty") String qty,
                                       @Field("catatan") String catatan);

}
