package com.example.appnhahang.manage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.Account;
import com.example.appnhahang.moder.Menu;
import com.example.appnhahang.moder.MenuDaDat;
import com.example.appnhahang.user.adapter.MenuChonAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChiTietMenuDatFragment extends Fragment {
    String tentk1="";
    TextView tvtentk_ct_menu,tvname_ct_menu,tvtenban_ct_menu,tvtongtien_menu,tvsdt_ct_menu;
    RecyclerView rclchitiet_menu;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<Account> list = new ArrayList<>();
    private ArrayList<Menu> list1 = new ArrayList<>();
    private ArrayList<MenuDaDat> list2 = new ArrayList<>();
    Button btn_nhan,btn_huy;
    String tenban,time;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    public ChiTietMenuDatFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_chi_tiet_menu_dat, container, false);

        tvtentk_ct_menu=view.findViewById(R.id.tvtentk_ct_menu);
        tvname_ct_menu=view.findViewById(R.id.tvname_ct_menu);
        tvtenban_ct_menu=view.findViewById(R.id.tvtenban_ct_menu);
        tvtongtien_menu=view.findViewById(R.id.tvtongtien_menu);
        tvsdt_ct_menu=view.findViewById(R.id.tvsdt_ct_menu);
        rclchitiet_menu=view.findViewById(R.id.rclchitiet_menu);
        btn_nhan=view.findViewById(R.id.btn_nhan);
        btn_huy=view.findViewById(R.id.btn_huy);
        Bundle b=getArguments();

        tentk1=b.getString("tentaikhoan");
        tenban = b.getString("tenban");
        time = b.getString("time");
        GetData();

        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclchitiet_menu.getContext(),
                linearLayoutManager.getOrientation());
        rclchitiet_menu.addItemDecoration(dividerItemDecoration);
        
        btn_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef=database.getReference("MenuDaChon");
                myRef.child(time).child("trangThai").setValue("Đã nhận");
                NhanDonFragment chitiet=new NhanDonFragment();
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, chitiet);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef=database.getReference("MenuDaChon");
                myRef.child(time).child("trangThai").setValue("Đã hủy");
                NhanDonFragment chitiet=new NhanDonFragment();
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, chitiet);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        return view;
    }
    private  void GetData(){


        DatabaseReference mdata=database.getReference("Account");
        mdata.child(tentk1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Object ten  = snapshot.child("name").getValue();
                    Object sdt  = snapshot.child("sdt").getValue();
                    tvname_ct_menu.setText("Họ Tên: "+ten);
                    tvsdt_ct_menu.setText("Số ĐIện Thoại: "+sdt);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });// xong truy van ho ten + sdt

//        DatabaseReference mdata3=database.getReference("MenuDaChon");
//        mdata3.child(tentk1).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
//                    Object tenban1  = snapshot.child("tenban").getValue();
//                    Object tongtien1  = snapshot.child("tongtien").getValue();
//                    tvtongtien_menu.setText("Tổng tiền: "+ );
//                    tvtenban_ct_menu.setText("Tên Bàn: "+tenban1);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        DatabaseReference edata = database.getReference("MenuDaChon");
        edata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    MenuDaDat menuDaDat = data.getValue(MenuDaDat.class);
                    menuDaDat.setTime(data.getKey());
                    if (menuDaDat.getTentk().equals(tentk1) && menuDaDat.getTenban().equals(tenban)){
                        tvtongtien_menu.setText("Tổng tiền: "+ String.valueOf(menuDaDat.getTongtien()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mdata2=database.getReference("LichSuMenu");
        mdata2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot data:snapshot.getChildren() ){
                    Menu menu1 = data.getValue(Menu.class);
                    menu1.setMaMon(data.getKey());
                    if(menu1.getTentk().equals(tentk1) && menu1.getTenban().equals(tenban)){
                        list1.add(menu1);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        rclchitiet_menu.setLayoutManager(linearLayoutManager);
                        MenuChonAdapter menuAdapter = new MenuChonAdapter(list1, getContext());
                        rclchitiet_menu.setAdapter(menuAdapter);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();

            }
        });
        tvtentk_ct_menu.setText("Tên Tài Khoản: "+tentk1);
        tvtenban_ct_menu.setText("Tên Bàn: "+tenban);
    }
}