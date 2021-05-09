package com.example.appnhahang.manage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appnhahang.R;


public class ThongTinChiaTietFragment extends Fragment {
    TextView tvtentkct,tvnamect,tvnamsinhct,tvsdtchitiet;
    String tentk,name,namsinh,sdt;

    public ThongTinChiaTietFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thong_tin_chia_tiet, container, false);
        tvtentkct=view.findViewById(R.id.tvtentk_ct);
        tvnamect=view.findViewById(R.id.tvname_ct);
        tvnamsinhct=view.findViewById(R.id.tvnamsinh_ct);
        tvsdtchitiet=view.findViewById(R.id.tvsdt_ct);

        Bundle b=getArguments();
        tentk=b.getString("tentk");
        name=b.getString("name");
        namsinh=b.getString("namsinh");
        sdt=b.getString("sdt");

        tvtentkct.setText("Tên Tài Khoản :"+tentk);
        tvnamect.setText("Họ Tên :"+name);
        tvnamsinhct.setText("Năm Sinh :"+namsinh);
        tvsdtchitiet.setText("Số Điện Thoại :"+sdt);


        return view;
    }
}