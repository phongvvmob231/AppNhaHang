package com.example.appnhahang.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.Account;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputLayout passmoi, passlai;
    String tentk1="";
    String pass1="";
    TextView tv_tenTK;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<Account> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passworld);
        setTitle("Đổi Mật Khẩu");

        tv_tenTK=findViewById(R.id.tv_tenTK);
        passmoi=findViewById(R.id.edPass_ĐMK);
        passlai=findViewById(R.id.edPassLai_ĐMK);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        tentk1=b.getString("tentk");
        tv_tenTK.setText("Tên Tài Khoản :"+tentk1);
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
                    for (int i = 0; i < list.size(); i++) {

                        if (list.get(i).getTentk().equals(tentk1)) {
                            pass1=list.get(i).getTentk();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void SavePassword(View view) {
        GetData();

//            checkPassword();
            String key=tentk1;
            String passlai12=passmoi.getEditText().getText().toString();

            DatabaseReference myRef = database.getReference("Account");
            myRef.child(key).child("pass").setValue(passlai12);
            Toast.makeText(this,"Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();


            Intent in=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
            startActivity(in);

    }
}