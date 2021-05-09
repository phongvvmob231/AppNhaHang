package com.example.appnhahang.user.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhahang.ItemClickSupport;
import com.example.appnhahang.R;
import com.example.appnhahang.moder.Menu;
import com.example.appnhahang.moder.MenuDaDat;
import com.example.appnhahang.moder.QuanLyBan;
import com.example.appnhahang.user.UserActivity;
import com.example.appnhahang.user.adapter.MenuChonAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class MenuChonFragment extends Fragment {
    RecyclerView rclMenuChon;
    TextView tvTongTienMenu;
    Button btn_thanhtoan;
    Spinner spnMaBanchon;
    List<MenuDaDat> menuList = new ArrayList<>();
    List<Menu> menuList1 = new ArrayList<>();
    List<Menu> menuList2 = new ArrayList<>();
    List<QuanLyBan> quanLyBanList = new ArrayList<>();
    StorageReference storageRef;
    FirebaseDatabase database;
    String maBanChon;

    public MenuChonFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_chon, container, false);
        btn_thanhtoan = view.findViewById(R.id.btn_thanhtoan);
        rclMenuChon = view.findViewById(R.id.rclDSMenuChon);
        tvTongTienMenu = view.findViewById(R.id.tvTongTienMenu);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        spnMaBanchon = view.findViewById(R.id.spnMaBan);
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        getData();

//        ItemClickSupport.addTo(rclMenuChon).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
//                ChiTietMonFragment chitiet=new ChiTietMonFragment();
//                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
//                Bundle b = new Bundle();
//                b.putString("tenmonchon",menuList1.get(i).getTenmon());
//                b.putString("giamonchon",menuList.get(i).getGia());
//                b.putString("anhmonchon",menuList.get(i).getAnhmon());
//                chitiet.setArguments(b);
//                transaction.replace(R.id.drawer_layout_user, chitiet);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
        DatabaseReference mdata1=database.getReference("QuanLyBan");
        mdata1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    QuanLyBan quanLyBan = data.getValue(QuanLyBan.class);
                    quanLyBan.setMaban(data.getKey());
                    quanLyBanList.add(quanLyBan);
                    ArrayAdapter<QuanLyBan> adapter = new ArrayAdapter<QuanLyBan>(getContext(), android.R.layout.simple_spinner_item, quanLyBanList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnMaBanchon.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spnMaBanchon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maBanChon = quanLyBanList.get(spnMaBanchon.getSelectedItemPosition()).getMaban();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String c = new SimpleDateFormat("dd-MM-yyyy    hh:mm:ss a").format(Calendar.getInstance().getTime());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef22=database.getReference().child("MenuDaChon").child(c);
                MenuDaDat menuDaDat=new MenuDaDat(UserActivity.tentaikhoan,maBanChon,Double.parseDouble(tvTongTienMenu.getText().toString()),c,"Đang xử lí");

                myRef22.setValue(menuDaDat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Đang nhập tb", Toast.LENGTH_SHORT).show();
                    }
                });


                DatabaseReference edata = database.getReference("LichSuMenu");
                for (Menu menu1 : menuList1){
                    if (UserActivity.tentaikhoan.equals(menu1.getTentk())){
                        edata.child(menu1.getMaMon()).child("tenban").setValue(maBanChon);
                    }
                }


                DatabaseReference databaseReference = database.getReference("MenuChon");
                for (Menu menu1 : menuList1){
                    if (UserActivity.tentaikhoan.equals(menu1.getTentk())){
                        databaseReference.child(menu1.getMaMon()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            }
                        });
                    }
                }

                LichSuFragment fragmentOne = new LichSuFragment();
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_user, fragmentOne);
                Toast.makeText(getContext(), "Đã Gọi Món", Toast.LENGTH_SHORT).show();
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ItemClickSupport.addTo(rclMenuChon).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                DatabaseReference databaseReference = database.getReference("LichSuMenu");
                databaseReference.child(menuList1.get(position).getMaMon()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    }
                });
                DatabaseReference databaseReference2 = database.getReference("MenuChon");
                databaseReference2.child(menuList1.get(position).getMaMon()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        getData();
                    }
                });
                return false;
            }
        });
        return view;
    }

    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mdata = database.getReference("MenuChon");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuList1.clear();
                double tongtien =0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Menu menu1 = data.getValue(Menu.class);
                    menu1.setMaMon(data.getKey());
                    if(menu1.getTentk().equals(UserActivity.tentaikhoan)){
                        menuList1.add(menu1);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        rclMenuChon.setLayoutManager(linearLayoutManager);
                        MenuChonAdapter menuAdapter = new MenuChonAdapter(menuList1, getContext());
                        rclMenuChon.setAdapter(menuAdapter);
                        tongtien = tongtien + Double.parseDouble(menu1.getGiaTien());
                        tvTongTienMenu.setText(String.valueOf(tongtien));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Lỗi ");
            }
        });

    }


}

