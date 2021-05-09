package com.example.appnhahang.user.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.Menu;
import com.example.appnhahang.moder.QuanLyMenu;
import com.example.appnhahang.user.UserActivity;
import com.example.appnhahang.user.fragment.ChiTietMonFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    StorageReference storageRef;
    FirebaseDatabase database;
    private List<QuanLyMenu> menuList;
    Context context;
    static int sl =1;

    public MenuAdapter(List<QuanLyMenu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(menuList.get(position).getAnhmon()).into(holder.imgHinhAnhMon);
        holder.tvTenMon.setText(menuList.get(position).getTenmon());
        holder.tvGiaTien.setText(String.valueOf(menuList.get(position).getGia()));
        holder.tvSoLong.setText("0");
        holder.cvMenu.setTag(position);


        holder.btnThem.setTag(position);
        holder.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = (Integer) v.getTag();
                int random = new Random().nextInt(89)+10;
                int random1 = new Random().nextInt(899)+100;

                String mamonchon = "NHOM"+ String.valueOf(random) + "img" + String.valueOf(random1);
                String tenmonchon = menuList.get(i).getTenmon();
                String giamonchon = menuList.get(i).getGia();
                String anhmonchon = menuList.get(i).getAnhmon();
                if (Integer.parseInt(holder.tvSoLong.getText().toString()) == 0){
                    holder.tvSoLong.setText("1");
                }else {
                    holder.tvSoLong.setText(String.valueOf(Integer.parseInt(holder.tvSoLong.getText().toString())+1));
                }

                FirebaseStorage storage = FirebaseStorage.getInstance();
                storageRef = storage.getReference();
                database = FirebaseDatabase.getInstance();
                String ban="";
                DatabaseReference myRef=database.getReference().child("MenuChon").child(mamonchon);
                Menu menu=new Menu(mamonchon,tenmonchon,giamonchon,anhmonchon, UserActivity.tentaikhoan,ban);

                myRef.setValue(menu).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                FirebaseStorage storage1 = FirebaseStorage.getInstance();
                storageRef = storage1.getReference();
                database = FirebaseDatabase.getInstance();
                String ban1="";
                DatabaseReference myRef1=database.getReference().child("LichSuMenu").child(mamonchon);

                myRef1.setValue(menu).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                sl++;
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhAnhMon;
        TextView tvTenMon;
        TextView tvGiaTien;
        TextView tvSoLong;
        Button btnThem;
        CardView cvMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhMon = itemView.findViewById(R.id.imgHinhAnhMon);
            tvTenMon = itemView.findViewById(R.id.tvTenMon);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTien);
            tvSoLong = itemView.findViewById(R.id.tvSoLuong);
            btnThem = itemView.findViewById(R.id.btnThem);
            cvMenu = itemView.findViewById(R.id.cvMenu);
        }
    }
}
