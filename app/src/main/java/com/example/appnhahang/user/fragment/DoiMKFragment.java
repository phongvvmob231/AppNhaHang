package com.example.appnhahang.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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


public class DoiMKFragment extends Fragment {
    TextInputLayout passcu, passmoi, passlai;
    Button btnluu, btnhuy;
    String pass1="";
    String tenTK = UserActivity.tentaikhoan;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<Account> list = new ArrayList<>();

    public DoiMKFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_doi_m_k, container, false);

        passcu = view.findViewById(R.id.edPassCu_tk_kh);
        passmoi = view.findViewById(R.id.edPassMoi_tk_kh);
        passlai = view.findViewById(R.id.edPassLai_tk_kh);
        btnluu = view.findViewById(R.id.btnLuuMK);
        btnhuy = view.findViewById(R.id.btnhuyMk);

        GetData();
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passcu11=passcu.getEditText().getText().toString();
                if(passcu11.equals(pass1)){
                    checkPassword();
                    String key=tenTK;
                    String passlai12=passmoi.getEditText().getText().toString();
                    Log.e("TAG", "pass moi: "+passlai12 );
                    DatabaseReference myRef = database.getReference("Account");
                    myRef.child(key).child("pass").setValue(passlai12);

                }else {
                    passcu.setError("Mật khẩu bạn nhập không đúng!");
                    Log.e("TAG", "onClick: "+pass1 );
                    Log.e("TAG", "onClick11: "+passcu.getEditText().getText().toString() );
                }
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
                        pass1=list.get(i).getPass();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String checkPassword() {
        passlai.setError(null);
        try{
            String pass1=passmoi.getEditText().getText().toString();
            String passlai1=passlai.getEditText().getText().toString();
            if(passlai1.length() == 0){
                passlai.setError("Vui lòng nhập mật khẩu!");
                return null;
            }

            if(pass1.equals(passlai1)){
                return pass1;
            }else{
                passlai.setError("Mật khẩu bạn nhập không đúng!");
                return null;
            }

        }catch (Exception e){
            passlai.setError("Mật khẩu không hợp lệ!");
            return null;
        }
    }
}