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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appnhahang.ItemClickSupport;
import com.example.appnhahang.R;
import com.example.appnhahang.manage.adapter.QuanLyTKAdapter;
import com.example.appnhahang.manage.table.UpdateBanFragment;
import com.example.appnhahang.moder.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TaiKhoanKHFragment extends Fragment {

    RecyclerView rclQuanLyTK;
    QuanLyTKAdapter adapter;
    private ArrayList<Account> list;
    LinearLayoutManager linearLayoutManager;


    public TaiKhoanKHFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_tai_khoan_k_h, container, false);

        rclQuanLyTK=view.findViewById(R.id.rclQuanLyTK);
        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclQuanLyTK.getContext(),
                linearLayoutManager.getOrientation());
        rclQuanLyTK.addItemDecoration(dividerItemDecoration);
        list=new ArrayList<>();
        GetData();

       return view;
    }

    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("Account");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "Load Data", Toast.LENGTH_SHORT).show();
                list.clear();

                for(DataSnapshot data:snapshot.getChildren() ){

                    Account taiKhoanKH=data.getValue(Account.class);
                    taiKhoanKH.setTentk(data.getKey());

                    list.add(taiKhoanKH);
                }

                for (int i = 0; i <list.size() ; i++) {
                    if(list.get(i).getMaql().equals("")){

                        adapter=new QuanLyTKAdapter(getContext(),list);
                        rclQuanLyTK.setLayoutManager(linearLayoutManager);
                        rclQuanLyTK.setAdapter(adapter);
                    }
                }

                ItemClickSupport.addTo(rclQuanLyTK).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {

                        ThongTinChiaTietFragment chitiet=new ThongTinChiaTietFragment();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle b = new Bundle();
                        b.putString("tentk",list.get(i).getTentk());
                        b.putString("name",list.get(i).getName());
                        b.putString("namsinh",list.get(i).getNamsinh());
                        b.putString("sdt",list.get(i).getSdt());
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
//                        AlertDialog.Builder adb=new AlertDialog.Builder(QuanLyTKActivity.this);
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
//                                        Toast.makeText(QuanLyTKActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.action_them).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}