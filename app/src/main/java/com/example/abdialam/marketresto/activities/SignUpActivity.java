package com.example.abdialam.marketresto.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.responses.ResponseAuth;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity{

    private Context mContext;
    private ProgressDialog progressDialog;
    private static final String TAG = "SignUpActivity";

    @BindView(R.id.editTextNama)
    EditText etNama;
    @BindView(R.id.editTextPhone)
    EditText etPhone;
    @BindView(R.id.editTextEmail)
    EditText etEmail;

    ApiService mApiSerivce;
    String value,message;

    @OnClick(R.id.buttonSignUp) void signup (){

        //create progres dialog
        progressDialog = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);

        //mengambil nilai inputan ke string
        String strNama = etNama.getText().toString();
        String strPhone = clearPhone(etPhone.getText().toString());
        String strEmail = etEmail.getText().toString();
        String token = SharedPrefManager.getInstance(mContext).getDeviceToken();
        String tipe = "konsumen";

         if(strNama.isEmpty()||strNama.equals(null)) {
             progressDialog.dismiss();
             etNama.setError("Nama diperlukan");
             etNama.requestFocus();
             return;
         }else if (strPhone.equals("62"))  {
            progressDialog.dismiss();
            etPhone.setError("Nomor telepon diperlukan");
            etPhone.requestFocus();
            return;
         }else if(strPhone.length()<12){
            progressDialog.dismiss();
            etPhone.setError("Nomor telepon tidak valid");
            etPhone.requestFocus();
            return;
         }else if(strPhone.isEmpty()||strPhone.equals(null)){
             progressDialog.dismiss();
             etPhone.setError("Nomor telepon diperlukan");
             etPhone.requestFocus();
             return;
         }else if(strEmail.isEmpty()||strEmail.equals(null)){
            progressDialog.dismiss();
            etEmail.setError("Email diperlukan");
            etEmail.requestFocus();
            return;
         }else if(!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
             progressDialog.dismiss();
             etEmail.setError("Email tidak valid");
             etEmail.requestFocus();
             return;
         }else if(token.isEmpty()||token.equals(null)){
             progressDialog.dismiss();
             etEmail.setError("Email diperlukan");
             etEmail.requestFocus();
             return;
         }else {



            mApiSerivce.signupRequest(strNama,strPhone,strEmail,token,tipe).enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    progressDialog.dismiss();
                    if(response.isSuccessful()){
                        value = response.body().getValue();
                        message = response.body().getMessage();
                        if(value.equals("1")){
                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext,SigninActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);

                        }else {
                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                    progressDialog.dismiss();
                    Toast.makeText(mContext, R.string.lostconnection, Toast.LENGTH_SHORT).show();
                }
            });
         }

    }





    @OnClick(R.id.textSignIn) void signin (){
        Intent intent = new Intent(mContext, SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mContext = this;
        mApiSerivce = ServerConfig.getAPIService();
        ButterKnife.bind(this);
    }



    public String clearPhone (String phoneNumber){
        String hp = phoneNumber.replaceAll("-","");
        String clearPhone = hp.substring(1,hp.length());
        return clearPhone;
    }


}

