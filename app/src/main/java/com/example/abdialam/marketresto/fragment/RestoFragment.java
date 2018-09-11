package com.example.abdialam.marketresto.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdialam.marketresto.R;

public class RestoFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view_frag_resto = inflater.inflate(R.layout.fragment_resto,container,false);
        return view_frag_resto;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}
