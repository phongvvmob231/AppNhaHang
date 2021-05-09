package com.example.appnhahang.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhahang.R;
import com.example.appnhahang.manage.adapter.NhanMenuAdapter;
import com.example.appnhahang.moder.Menu;
import com.example.appnhahang.moder.MenuDaDat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.ViewHolder> {
    ArrayList<MenuDaDat> list;

    public Context context;
    LayoutInflater inflater;

    public LichSuAdapter(Context context, ArrayList<MenuDaDat> list) {
        this.list = list;
        this.context = context;

        inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View   view=inflater.inflate(R.layout.item_lich_su,null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuDaDat quanLyMenu=list.get(position);
        holder.tvtenban_ban.setText("Tên Bàn :"+"  "+quanLyMenu.getTenban());
        holder.tvtime_menu.setText(quanLyMenu.getTime());
        holder.tvtrang_thai_menu.setText(quanLyMenu.getTrangThai());
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private  View itemview;
        TextView tvtenban_ban,tvtime_menu,tvtrang_thai_menu;
        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            tvtenban_ban = itemView.findViewById(R.id.tvtenban_menu);
            tvtime_menu=itemView.findViewById(R.id.tvtime_menu);
            tvtrang_thai_menu = itemView.findViewById(R.id.tvtrang_thai_menu);
        }
    }
}
