package com.example.appnhahang.manage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appnhahang.ItemClickSupport;
import com.example.appnhahang.MainActivity;
import com.example.appnhahang.R;
import com.example.appnhahang.manage.adapter.NhanMenuAdapter;
import com.example.appnhahang.moder.MenuDaDat;
import com.example.appnhahang.user.fragment.ChiTietMonFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NhanDonFragment extends Fragment {

    RecyclerView rlcNhanMenu;
    NhanMenuAdapter adapter;
    private ArrayList<MenuDaDat> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    public NhanDonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_nhan_don, container, false);

        rlcNhanMenu = view.findViewById(R.id.rlcNhanMenu);
        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rlcNhanMenu.getContext(),
                linearLayoutManager.getOrientation());
        rlcNhanMenu.addItemDecoration(dividerItemDecoration);
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
                    list.add(quanLyMenu);
                    adapter=new NhanMenuAdapter(getContext(),list);
                    rlcNhanMenu.setLayoutManager(linearLayoutManager);
                    rlcNhanMenu.setAdapter(adapter);
                }
                ItemClickSupport.addTo(rlcNhanMenu).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                        ChiTietMenuDatFragment chitiet=new ChiTietMenuDatFragment();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle b = new Bundle();
                        b.putString("tentaikhoan",list.get(i).getTentk());
                        b.putString("time",list.get(i).getTime());
                        b.putString("tenban",list.get(i).getTenban());
                        chitiet.setArguments(b);
                        transaction.replace(R.id.frameLayout, chitiet);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
//                ItemClickSupport.addTo(rclQuanLyBan).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClicked(RecyclerView recyclerView, int i, View v) {
//
//
//                        AlertDialog.Builder adb=new AlertDialog.Builder(DanhSachBanActivity.this);
//                        adb.setTitle("Xóa: ");
//                        adb.setMessage(Html.fromHtml(
//                                "Bạn có muốn xóa Bàn: <font color='#ff4343'>"
//                                        + list.get(i).getMaban() + "</font> khỏi danh sách?"));
//                        adb.setNegativeButton("Cancel", null);
//                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                FirebaseDatabase database=FirebaseDatabase.getInstance();
//                                DatabaseReference mRe=database.getReference("QuanLyBan" +
//                                        "");
//                                mRe.child(list.get(i).getMaban()).removeValue(new DatabaseReference.CompletionListener() {
//                                    @Override
//                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                        Toast.makeText(DanhSachBanActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }});
//                        adb.show();
//
//                        return false;
//                    }
//                });
                Log.e("TAG", "ds: "+list );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }






}