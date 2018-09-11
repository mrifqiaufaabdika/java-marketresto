package com.example.abdialam.marketresto.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.config.ServerConfig;
import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.responses.ResponseAuth;
import com.example.abdialam.marketresto.rest.ApiService;
import com.example.abdialam.marketresto.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    @BindView(R.id.editTextPhone) EditText editTextPhone;
    @BindView(R.id.buttonGetVerificationCode) Button btnGVC;
    @BindView(R.id.textSignUp) TextView textSignUp ;



    Context mContext ;
    String value,message;
    ApiService mApiService ;
    ProgressDialog progressDialog;
    SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mContext = this;
        ButterKnife.bind(this);
        mApiService = ServerConfig.getAPIService();
        sessionManager = new SessionManager(this);
        //Firebase Auth Instance

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/MavenPro-Regular.ttf");
        editTextPhone.setTypeface(type);

    }


    @OnClick(R.id.buttonGetVerificationCode) void getCode (){
        progressDialog = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);

        final String phone = clearPhone(editTextPhone.getText().toString());

        if(phone.equals("62")){
            progressDialog.dismiss();
            editTextPhone.setError("Nomor telepon diperlukan");
            editTextPhone.requestFocus();
            return;
        }

        if(phone.length() < 12){
            progressDialog.dismiss();
            editTextPhone.setError("Nomor telepon tidak valid");
            editTextPhone.requestFocus();
            return;
        }

        mApiService.signinRequest(phone).enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){

                    value = response.body().getValue();
                    message = response.body().getMessage();
                    if(value.equals("1")){
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SigninActivity.this, VerifyActifity.class);
                        intent.putExtra("phone",phone);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();



                        //sendVerificationCode();
                    }else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, R.string.lostconnection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.textSignUp) void toSign (){
        Intent intent = new Intent(mContext, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }



    //untuk menggambil no hp
    public String clearPhone (String phoneNumber){
        String hp = phoneNumber.replaceAll("-","");
        String clearPhone = hp.substring(1,hp.length());
        return clearPhone;
    }


}
