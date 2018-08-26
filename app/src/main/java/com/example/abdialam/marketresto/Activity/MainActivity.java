package com.example.abdialam.marketresto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView tvPhoneNumber;
    Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPhoneNumber=findViewById(R.id.tv_phone_number);
        btnSignOut = findViewById(R.id.btn_sign_out);


        setPhoneNumber();

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void setPhoneNumber(){
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        try{
            tvPhoneNumber.setText(user.getPhoneNumber());
        }catch (Exception e){
            Toast.makeText(this,"Phone Number Not Found",Toast.LENGTH_SHORT).show();
        }
    }
}
