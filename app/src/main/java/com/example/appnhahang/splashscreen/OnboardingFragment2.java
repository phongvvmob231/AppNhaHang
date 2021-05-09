package com.example.appnhahang.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.appnhahang.R;
import com.example.appnhahang.login.LoginActivity;


public class OnboardingFragment2 extends Fragment {


    Button btn_skip;
    public OnboardingFragment2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_onboarding2, container, false);
        btn_skip=view.findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(container.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}