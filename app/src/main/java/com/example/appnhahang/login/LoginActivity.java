package com.example.appnhahang.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appnhahang.MainActivity;
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

public class LoginActivity extends AppCompatActivity {
    TextInputLayout edTenTKDangNhap,edPassDangNhap;
    List<Account> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng Nhập");

        edTenTKDangNhap=findViewById(R.id.edTenTKDangNhap);
        edPassDangNhap=findViewById(R.id.edPassDangNhap);

    }

    public void ChangePassword(View view) {
        Intent i=new Intent(this,ChangePasswordActivity.class);
        startActivity(i);
    }

    public void Login(View view) {
        GetData();
    }

    public void SignUp(View view) {
        Intent i=new Intent(this,SignUpActivity.class);
        startActivity(i);
    }

    private  void GetData(){
        String tentk=edTenTKDangNhap.getEditText().getText().toString();
        String pass=edPassDangNhap.getEditText().getText().toString();
        FirebaseDatabase database=FirebaseDatabase.getInstance();


        DatabaseReference mdata=database.getReference("Account");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren() ){

                    Account account=data.getValue(Account.class);
                    account.setTentk(data.getKey());

                    list.add(account);
                }


                for (int i = 0; i <list.size() ; i++) {
                    if(tentk.equals(list.get(i).getTentk()) && pass.equals(list.get(i).getPass())) {
                        if(list.get(i).getMaql().equals("1234567890")){
                            Toast.makeText(LoginActivity.this, "Chào mừng Admin!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            Bundle b=new Bundle();
                            b.putString("tentk",list.get(i).getTentk());
                            b.putString("sdt",list.get(i).getSdt());

                            intent.putExtras(b);
                            startActivity(intent);
                            break;

                        }else {
                            Toast.makeText(LoginActivity.this, "Đăng Nhập THành công!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, UserActivity.class);
                            Bundle b=new Bundle();
                            b.putString("tentk",list.get(i).getTentk());
                            b.putString("sdt",list.get(i).getSdt());
                            intent.putExtras(b);
                            startActivity(intent);
                            break;
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        });

    }


}