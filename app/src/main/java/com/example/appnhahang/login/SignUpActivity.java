package com.example.appnhahang.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout edTenTK,edName,edPass,edPassLai,edNamSinh,edSDT,edMaQL;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Đăng Ký");

        edTenTK=findViewById(R.id.edTenTK);
        edPass=findViewById(R.id.edPass);
        edPassLai=findViewById(R.id.edPassLai);
        edName=findViewById(R.id.edName);
        edNamSinh=findViewById(R.id.edNamSinh);
        edSDT=findViewById(R.id.edSDT);
        edMaQL=findViewById(R.id.edMaQL);



        database = FirebaseDatabase.getInstance();



    }


    public void SignUp(View view) {

        String pass=checkPassword();
        String tentk=edTenTK.getEditText().getText().toString();

        String hoten=edName.getEditText().getText().toString();
        String namsinh=edNamSinh.getEditText().getText().toString();
        String sdt=edSDT.getEditText().getText().toString();
        String maql=edMaQL.getEditText().getText().toString();


        String key=tentk;



        DatabaseReference myRef1=database.getReference().child("Account");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(tentk)) {
                    Toast.makeText(SignUpActivity.this, "Tài khoản đã có", Toast.LENGTH_SHORT).show();
                } else {
                    if(maql.equals("1234567890")){
                        DatabaseReference myRef=database.getReference().child("Account").child(key);
                        Account taiKhoanKH=new Account(tentk,pass,hoten,namsinh,sdt,maql);
                        myRef.setValue(taiKhoanKH).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUpActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        String maqlkh="";
                        DatabaseReference myRef=database.getReference().child("Account").child(key);
                        Account taiKhoanKH=new Account(tentk,pass,hoten,namsinh,sdt,maqlkh);
                        myRef.setValue(taiKhoanKH).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUpActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Intent i=new Intent(this, LoginActivity.class);
        startActivity(i);

    }

    private String checkPassword() {
        edPassLai.setError(null);
        try{
            String pass1=edPass.getEditText().getText().toString();
            String passlai=edPassLai.getEditText().getText().toString();
            if(passlai.length() == 0){
                edPassLai.setError("Vui lòng nhập mật khẩu!");
                return null;
            }

            if(pass1.equals(passlai)){
                return pass1;
            }else{
                edPassLai.setError("Mật khẩu bạn nhập không đúng!");
                return null;
            }

        }catch (Exception e){
            edPassLai.setError("Mật khẩu không hợp lệ!");
            return null;
        }
    }
}