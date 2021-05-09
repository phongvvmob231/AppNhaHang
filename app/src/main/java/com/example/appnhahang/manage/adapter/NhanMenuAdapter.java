package com.example.appnhahang.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.MenuDaDat;

import java.util.ArrayList;

public class NhanMenuAdapter extends RecyclerView.Adapter<NhanMenuAdapter.ViewHolder> {

        ArrayList<MenuDaDat> list;

public Context context;
        LayoutInflater inflater;



public NhanMenuAdapter(Context context, ArrayList<MenuDaDat> list) {
        this.list = list;
        this.context = context;

        inflater= (LayoutInflater) context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
        }


public class ViewHolder extends RecyclerView.ViewHolder {
    private View itemview;
    TextView tvtentk_ban,tvtenban_ban,tvtime_menu,tvtrang_thai_menu;


    public ViewHolder(View itemView) {
        super(itemView);
        itemview = itemView;
        tvtentk_ban = itemView.findViewById(R.id.tvtentk_menu);
        tvtenban_ban = itemView.findViewById(R.id.tvtenban_menu);
        tvtime_menu=itemView.findViewById(R.id.tvtime_menu);
        tvtrang_thai_menu = itemView.findViewById(R.id.tvtrang_thai_menu);

        //Xử lý khi nút Chi tiết được bấm

    }
}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View   view=inflater.inflate(R.layout.item_nhan_menu,null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holde, int position) {

        MenuDaDat quanLyMenu=list.get(position);
        holde.tvtentk_ban.setText("Tài Khoản:"+"  "+quanLyMenu.getTentk());
        holde.tvtenban_ban.setText("Tên Bàn :"+"  "+quanLyMenu.getTenban());
        holde.tvtime_menu.setText(quanLyMenu.getTime());
        holde.tvtrang_thai_menu.setText(quanLyMenu.getTrangThai());
        holde.itemView.setTag(position);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
