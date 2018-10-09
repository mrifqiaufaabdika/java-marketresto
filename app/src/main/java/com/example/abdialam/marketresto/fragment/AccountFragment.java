package com.example.abdialam.marketresto.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.activities.SigninActivity;
import com.example.abdialam.marketresto.utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountFragment extends Fragment {

    Context mContext ;
    SessionManager sessionManager;
    TextView tvNamaUser, tvPhoneUser, tvEmailUser,tvBalance,btnBantuan,btnLayanan,btnPrivasi,signout;
    ImageButton edit;
    HashMap<String,String> user;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);


        mContext =getActivity();
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getUserDetail();
        signout = (TextView) view.findViewById(R.id.btn_sign_out);
        init(view);

        setValue(view);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sessionManager.logoutUser();
                Intent intent = new Intent(mContext, SigninActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return  view;
    }

    private void setValue(View view) {
        tvNamaUser.setText(user.get(SessionManager.NAMA_LENGKAP));
        tvPhoneUser.setText("+"+user.get(SessionManager.NO_HP));
        tvEmailUser.setText(user.get(SessionManager.EMAIL));
    }

    private void init(View view) {
        tvNamaUser = (TextView) view.findViewById(R.id.tvNamaUser);
        tvPhoneUser = (TextView) view.findViewById(R.id.tvPhoneUser);
        tvEmailUser = (TextView)view.findViewById(R.id.tvEmailUser);
        tvBalance = (TextView) view.findViewById(R.id.tvBalance);
    }


    //    @OnClick(R.id.btn_sign_out) void signOut (){
//        FirebaseAuth.getInstance().signOut();
//        sessionManager.logoutUser();
//        Intent intent = new Intent(mContext, SigninActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//    }
}
