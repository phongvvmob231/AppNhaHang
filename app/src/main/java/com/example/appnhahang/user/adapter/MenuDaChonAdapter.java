

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
import com.example.appnhahang.moder.MenuDaDat;

import java.util.List;

public class MenuDaChonAdapter extends RecyclerView.Adapter<MenuDaChonAdapter.ViewHolder> {

    List<MenuDaDat> menuList;
    Context context;

    public MenuDaChonAdapter(List<MenuDaDat> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_chon,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvTenMonChon.setText(menuList.get(position).getTentk());
        holder.tvGiaMonChon.setText(menuList.get(position).getTenban());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhAnhMonChon;
        TextView tvTenMonChon;
        TextView tvGiaMonChon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhMonChon = itemView.findViewById(R.id.imgHinhAnhMonChon);
            tvTenMonChon = itemView.findViewById(R.id.tvTenMonChon);
            tvGiaMonChon = itemView.findViewById(R.id.tvGiaTienChon);
        }
    }
}
