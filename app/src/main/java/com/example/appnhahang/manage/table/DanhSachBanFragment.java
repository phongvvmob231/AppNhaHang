package com.example.appnhahang.manage.table;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appnhahang.ItemClickSupport;
import com.example.appnhahang.R;
import com.example.appnhahang.manage.adapter.QuanLyBanAdapter;
import com.example.appnhahang.moder.QuanLyBan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DanhSachBanFragment extends Fragment {

    RecyclerView rclQuanLyBan;
    QuanLyBanAdapter adapter;
    private ArrayList<QuanLyBan> list;
    LinearLayoutManager linearLayoutManager;
    public DanhSachBanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_danh_sach_ban, container, false);

        rclQuanLyBan=view.findViewById(R.id.rclQuanLyBan);
        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclQuanLyBan.getContext(),
                linearLayoutManager.getOrientation());
        rclQuanLyBan.addItemDecoration(dividerItemDecoration);


        list=new ArrayList<>();
        GetData();

        return view;
    }

    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("QuanLyBan");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "Load Data", Toast.LENGTH_SHORT).show();
                list.clear();

                for(DataSnapshot data:snapshot.getChildren() ){

                    QuanLyBan quanLyBan=data.getValue(QuanLyBan.class);
                    quanLyBan.setMaban(data.getKey());

                    list.add(quanLyBan);
                    Log.e("TAG", "onCreate: "+snapshot.toString());
                    Log.e("TAG", "onCreate: "+list);

                    adapter=new QuanLyBanAdapter(getContext(),list);
                    rclQuanLyBan.setLayoutManager(linearLayoutManager);
                    rclQuanLyBan.setAdapter(adapter);
                }

                ItemClickSupport.addTo(rclQuanLyBan).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {

                        UpdateBanFragment chitiet=new UpdateBanFragment();
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle b = new Bundle();
                        b.putString("maban",list.get(i).getMaban());
                        b.putString("img",list.get(i).getAnhban());
                        b.putString("trangthai",list.get(i).getTrangthai());
                        b.putString("ghichu",list.get(i).getGhichu());
                        chitiet.setArguments(b);
                        transaction.replace(R.id.frameLayout, chitiet);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                });
                ItemClickSupport.addTo(rclQuanLyBan).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int i, View v) {


                        AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
                        adb.setTitle("Xóa: ");
                        adb.setMessage(Html.fromHtml(
                                "Bạn có muốn xóa Bàn: <font color='#ff4343'>"
                                        + list.get(i).getMaban() + "</font> khỏi danh sách?"));
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                DatabaseReference mRe=database.getReference("QuanLyBan" +
                                        "");
                                mRe.child(list.get(i).getMaban()).removeValue(new DatabaseReference.CompletionListener() {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_them){
            ThemBanFragment fragmentOne = new ThemBanFragment();
            FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragmentOne);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}