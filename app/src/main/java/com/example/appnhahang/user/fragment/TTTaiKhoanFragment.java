package com.example.appnhahang.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.Account;
import com.example.appnhahang.user.UserActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TTTaiKhoanFragment extends Fragment {
    TextInputLayout name, sdt, namsinh;
    Button btnluu, btnhuy;
    TextView tvTentk;
    String tenTK = UserActivity.tentaikhoan;
    String Name, SDT, NamSinh;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<Account> list = new ArrayList<>();
    public TTTaiKhoanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_t_t_tai_khoan, container, false);
        tvTentk = view.findViewById(R.id.tvtentk_ct_kh);
        name = view.findViewById(R.id.edName_ct_kh);
        sdt = view.findViewById(R.id.edSDT_ct_kh);
        namsinh = view.findViewById(R.id.edNamSinh_ct_kh);
        btnluu = view.findViewById(R.id.btnLuuTk);
        btnhuy = view.findViewById(R.id.btnhuy);


        GetData();



        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = tenTK;
                String name1 = name.getEditText().getText().toString();
                String namsinh1 = namsinh.getEditText().getText().toString();
                String sdt1 = sdt.getEditText().getText().toString();
                DatabaseReference myRef = database.getReference("Account");
                myRef.child(key).child("name").setValue(name1);
                myRef.child(key).child("namsinh").setValue(namsinh1);
                myRef.child(key).child("sdt").setValue(sdt1);
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuKHFragment fragmentOne = new MenuKHFragment();
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frameLayout_user, fragmentOne);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void GetData() {
        DatabaseReference mdata = database.getReference("Account");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {

                    Account taiKhoanKH = data.getValue(Account.class);
                    taiKhoanKH.setTentk(data.getKey());

                    list.add(taiKhoanKH);
                }
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).getTentk().equals(tenTK)) {
                        Name = list.get(i).getName();
                        SDT = list.get(i).getSdt();
                        NamSinh = list.get(i).getNamsinh();

                        tvTentk.setText(tenTK);
                        name.getEditText().setText(Name);
                        sdt.getEditText().setText(SDT);
                        namsinh.getEditText().setText(NamSinh);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}