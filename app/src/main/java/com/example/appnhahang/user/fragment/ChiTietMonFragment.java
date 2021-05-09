package com.example.appnhahang.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.appnhahang.R;
import com.squareup.picasso.Picasso;


public class ChiTietMonFragment extends Fragment {
    ImageView imgChiTietAnhMon;
    TextView tvChiTietTenMon,tvChiTietGiaMon;
    String anhmonchon,tenmonchon,giamonchon;

    public ChiTietMonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chi_tiet_mon_fragment, container, false);

        imgChiTietAnhMon = view.findViewById(R.id.imgChiTietAnhMon);
        tvChiTietTenMon = view.findViewById(R.id.tvChiTietTenMon);
        tvChiTietGiaMon = view.findViewById(R.id.tvChiTietGiaMon);

        Bundle b=getArguments();
        anhmonchon =b.getString("anhmonchon");
        tenmonchon =b.getString("tenmonchon");
        giamonchon =b.getString("giamonchon");

        tvChiTietGiaMon.setText(giamonchon);
        tvChiTietTenMon.setText(tenmonchon);
        Picasso.get().load(anhmonchon).into(imgChiTietAnhMon);
        return view;




    }
}