package com.example.appnhahang.manage.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appnhahang.ItemClickSupport;
import com.example.appnhahang.R;
import com.example.appnhahang.manage.adapter.QuanLyMenuAdapter;
import com.example.appnhahang.manage.table.ThemBanFragment;
import com.example.appnhahang.manage.table.UpdateBanFragment;
import com.example.appnhahang.moder.QuanLyMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DanhSachMenuFragment extends Fragment {
    RecyclerView rclQuanLyMenu;
    QuanLyMenuAdapter adapter;
    private ArrayList<QuanLyMenu> list;
    LinearLayoutManager linearLayoutManager;
    public DanhSachMenuFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_danh_sach_menu, container, false);
        rclQuanLyMenu=view.findViewById(R.id.rclQuanLyMenu);
        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclQuanLyMenu.getContext(),linearLayoutManager.getOrientation());
        rclQuanLyMenu.addItemDecoration(dividerItemDecoration);
        list=new ArrayList<>();
        GetData();
        return view;
    }

    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("QuanLyMenu");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "Load Data", Toast.LENGTH_SHORT).show();
                list.clear();
                for(DataSnapshot data:snapshot.getChildren() ){

                    QuanLyMenu quanLyMenu=data.getValue(QuanLyMenu.class);
                    quanLyMenu.setMamon(data.getKey());

                    list.add(quanLyMenu);
//                    Log.e("TAG", "onCreate: "+snapshot.toString());
//                    Log.e("TAG", "onCreate: "+list);

                    adapter=new QuanLyMenuAdapter(getContext(),list);
                    rclQuanLyMenu.setLayoutManager(linearLayoutManager);
                    rclQuanLyMenu.setAdapter(adapter);
                }

                ItemClickSupport.addTo(rclQuanLyMenu).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                        UpdateMenuFragment chitiet=new UpdateMenuFragment();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle b = new Bundle();
                        b.putString("mamon",list.get(i).getMamon());
                        b.putString("img",list.get(i).getAnhmon());
                        b.putString("tenmon",list.get(i).getTenmon());
                        b.putString("giatien",list.get(i).getGia());
                        chitiet.setArguments(b);
                        transaction.replace(R.id.frameLayout, chitiet);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                ItemClickSupport.addTo(rclQuanLyMenu).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int i, View v) {


                        AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
                        adb.setTitle("Xóa: ");
                        adb.setMessage(Html.fromHtml(
                                "Bạn có muốn xóa Bàn: <font color='#ff4343'>"
                                        + list.get(i).getMamon() + "</font> khỏi danh sách?"));
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                DatabaseReference mRe=database.getReference("QuanLyMenu");
                                mRe.child(list.get(i).getMamon()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(getContext(), "Xóa Thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }});
                        adb.show();

                        return false;
                    }
                });
                Log.e("TAG", "ds: "+list );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_them){
            ThemMenuFragment fragmentOne = new ThemMenuFragment();
            FragmentTransaction transaction =this.getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.frameLayout, fragmentOne);
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