package com.example.appnhahang.user.fragment;

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
import android.widget.Toast;

import com.example.appnhahang.ItemClickSupport;
import com.example.appnhahang.R;
import com.example.appnhahang.manage.ChiTietMenuDatFragment;
import com.example.appnhahang.manage.adapter.NhanMenuAdapter;
import com.example.appnhahang.moder.Menu;
import com.example.appnhahang.moder.MenuDaDat;
import com.example.appnhahang.user.UserActivity;
import com.example.appnhahang.user.adapter.LichSuAdapter;
import com.example.appnhahang.user.adapter.MenuChonAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class LichSuFragment extends Fragment {

    RecyclerView rclHistoryMenu;
    LichSuAdapter adapter;
    private ArrayList<MenuDaDat> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    public LichSuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lich_su, container, false);
        rclHistoryMenu = view.findViewById(R.id.rclHistoryMenu);
        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclHistoryMenu.getContext(),
                linearLayoutManager.getOrientation());
        rclHistoryMenu.addItemDecoration(dividerItemDecoration);
        GetData();

        return view;
    }
    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("MenuDaChon");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data:snapshot.getChildren() ){
                    MenuDaDat quanLyMenu=data.getValue(MenuDaDat.class);
                    quanLyMenu.setTime(data.getKey());
                    if (quanLyMenu.getTentk().equals(UserActivity.tentaikhoan)) {
                        list.add(quanLyMenu);
                        adapter = new LichSuAdapter(getContext(), list);
                        rclHistoryMenu.setLayoutManager(linearLayoutManager);
                        rclHistoryMenu.setAdapter(adapter);
                    }
                }
                ItemClickSupport.addTo(rclHistoryMenu).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                        ChiTietLichSuFragment chitiet=new ChiTietLichSuFragment();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle b = new Bundle();
                        b.putString("tenban",list.get(i).getTenban());
                        chitiet.setArguments(b);
                        transaction.replace(R.id.drawer_layout_user, chitiet);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }
}