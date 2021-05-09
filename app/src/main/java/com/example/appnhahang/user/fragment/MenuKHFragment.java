package com.example.appnhahang.user.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhahang.ItemClickSupport;
import com.example.appnhahang.R;
import com.example.appnhahang.manage.menu.ThemMenuFragment;
import com.example.appnhahang.manage.menu.UpdateMenuFragment;
import com.example.appnhahang.moder.QuanLyMenu;
import com.example.appnhahang.user.adapter.MenuAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class MenuKHFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    EditText edTimKiemMenu;
    RecyclerView rclDSMenu;
    StorageReference storageReference;
    List<QuanLyMenu> menuList = new ArrayList<>();

    public MenuKHFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu_k_h, container, false);
        edTimKiemMenu = view.findViewById(R.id.edTimKiemMenu);
        rclDSMenu =view. findViewById(R.id.rclDSMenu);

        firebaseDatabase = FirebaseDatabase.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();
        getData();

        ItemClickSupport.addTo(rclDSMenu).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                ChiTietMonFragment chitiet=new ChiTietMonFragment();
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                Bundle b = new Bundle();

                b.putString("tenmonchon",menuList.get(i).getTenmon());
                b.putString("giamonchon",menuList.get(i).getGia());
                b.putString("anhmonchon",menuList.get(i).getAnhmon());
                chitiet.setArguments(b);

                transaction.replace(R.id.frameLayout_user, chitiet);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        edTimKiemMenu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mdata = database.getReference("QuanLyMenu");

                mdata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        menuList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            QuanLyMenu quanLyMenu = ds.getValue(QuanLyMenu.class);
                            quanLyMenu.setMamon(ds.getKey());
                            if (quanLyMenu.getTenmon().contains(edTimKiemMenu.getText().toString())){
                                menuList.add(quanLyMenu);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                rclDSMenu.setLayoutManager(linearLayoutManager);
                                MenuAdapter menuAdapter = new MenuAdapter(menuList,getContext());
                                rclDSMenu.setAdapter(menuAdapter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mdata = database.getReference("QuanLyMenu");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                menuList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {

                    QuanLyMenu quanLyMenu = data.getValue(QuanLyMenu.class);
                    quanLyMenu.setMamon(data.getKey());
                    menuList.add(quanLyMenu);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rclDSMenu.setLayoutManager(linearLayoutManager);
                    MenuAdapter menuAdapter = new MenuAdapter(menuList,getContext());
                    rclDSMenu.setAdapter(menuAdapter);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_mua){
            MenuChonFragment fragmentOne = new MenuChonFragment();
            FragmentTransaction transaction =this.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout_user, fragmentOne);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}