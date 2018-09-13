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

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.activities.SigninActivity;
import com.example.abdialam.marketresto.utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountFragment extends Fragment {

    Context mContext ;
    SessionManager sessionManager;
    Button signout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);


        mContext =getActivity();
        sessionManager = new SessionManager(mContext);
        signout = (Button) view.findViewById(R.id.btn_sign_out);

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





    //    @OnClick(R.id.btn_sign_out) void signOut (){
//        FirebaseAuth.getInstance().signOut();
//        sessionManager.logoutUser();
//        Intent intent = new Intent(mContext, SigninActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//    }
}
