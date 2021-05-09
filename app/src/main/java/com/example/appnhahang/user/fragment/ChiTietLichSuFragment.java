package com.example.appnhahang.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.appnhahang.user.UserActivity;
import com.example.appnhahang.user.adapter.MenuChonAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChiTietLichSuFragment extends Fragment {
    TextView tvChiTietTongTien;
    RecyclerView rclchitiet_menu;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<Menu> list1 = new ArrayList<>();
    private ArrayList<MenuDaDat> list2 = new ArrayList<>();
    String tenban;
    FirebaseDatabase database=FirebaseDatabase.getInstance();


    public ChiTietLichSuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_lich_su, container, false);
        tvChiTietTongTien= view.findViewById(R.id.tvChiTietTongTien);
        rclchitiet_menu = view.findViewById(R.id.rclDSChiTietlichSu);

        Bundle b=getArguments();
        tenban=b.getString("tenban");

        GetData();
        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclchitiet_menu.getContext(),
                linearLayoutManager.getOrientation());
        rclchitiet_menu.addItemDecoration(dividerItemDecoration);
        return view;
    }
    private  void GetData(){

        DatabaseReference mdata3=database.getReference("MenuDaChon");
        mdata3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    MenuDaDat menuDaDat = data.getValue(MenuDaDat.class);

                    if (menuDaDat.getTentk().equals(UserActivity.tentaikhoan)  && menuDaDat.getTenban().equals(tenban)){
                        tvChiTietTongTien.setText("Tổng Tiền : "+menuDaDat.getTongtien());
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
                for(DataSnapshot data:snapshot.getChildren() ) {
                    Menu menu1 = data.getValue(Menu.class);
                    menu1.setMaMon(data.getKey());
                    if (menu1.getTentk().equals(UserActivity.tentaikhoan) && menu1.getTenban().equals(tenban)) {
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
    }
}