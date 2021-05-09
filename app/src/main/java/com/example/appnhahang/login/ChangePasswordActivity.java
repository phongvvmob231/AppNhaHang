package com.example.appnhahang.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class ChangePasswordActivity extends AppCompatActivity {
    TextInputLayout tentk,name,sdt,namsinh;
    List<Account> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Lấy Lại Mật Khẩu");

        tentk=findViewById(R.id.edTenTK_QMK);
        name=findViewById(R.id.edName_QMK);
        sdt=findViewById(R.id.edSDT_QMK);
        namsinh=findViewById(R.id.edNamSinh_QMK);

    }
    public void changepassword(View view) {
        GetData();
    }
    private  void GetData(){
        String tentk1=tentk.getEditText().getText().toString();
        String name1=name.getEditText().getText().toString();
        String sdt1=sdt.getEditText().getText().toString();
        String namsinh1=namsinh.getEditText().getText().toString();
        FirebaseDatabase database=FirebaseDatabase.getInstance();


        DatabaseReference mdata=database.getReference("Account");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren() ){
                    Account taiKhoanKH=data.getValue(Account.class);
                    taiKhoanKH.setTentk(data.getKey());
                    list.add(taiKhoanKH);
                }


                for (int i = 0; i <list.size() ; i++) {
                    if(tentk1.equals(list.get(i).getTentk())&&name1.equals(list.get(i).getName())&&sdt1.equals(list.get(i).getSdt())&&namsinh1.equals(list.get(i).getNamsinh())){

                        Intent intent=new Intent(ChangePasswordActivity.this, ForgotPasswordActivity.class);
                        Bundle b=new Bundle();
                        b.putString("tentk",list.get(i).getTentk());
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();

                    }



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChangePasswordActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        });

    }

}