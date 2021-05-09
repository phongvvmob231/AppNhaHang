package com.example.appnhahang.user.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appnhahang.R;


public class LienHeFragment extends Fragment {

    TextView ct, sdt, gmail, diachi;

    public LienHeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lien_he, container, false);
        ct = view.findViewById(R.id.tvtenct);
        sdt = view.findViewById(R.id.tvsdtch);
        gmail = view.findViewById(R.id.tvgmail);
        diachi = view.findViewById(R.id.tvdiachi);
        ct.setText("Cửa hàng Foodybite");
        sdt.setText("19008848");
        gmail.setText("foodybite@gmail.com");
        diachi.setText("Tòa Nhà 19A,Đường Thanh Xuân ,Quận Thanh Xuân ,Hà Nội");
        return view;
    }
}