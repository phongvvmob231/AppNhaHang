package com.example.appnhahang.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.QuanLyMenu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuanLyMenuAdapter extends  RecyclerView.Adapter<QuanLyMenuAdapter.ViewHolder> {
    List<QuanLyMenu> list;

    public Context context;
    LayoutInflater inflater;



    public QuanLyMenuAdapter(Context context, List<QuanLyMenu> list) {
        this.list = list;
        this.context = context;

        inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        TextView tvmamon,tvtenmon,tvgiatien;
        ImageView imgmon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            tvmamon = itemView.findViewById(R.id.tvmamon);
            tvtenmon = itemView.findViewById(R.id.tvtenmon);
            tvgiatien = itemView.findViewById(R.id.tvgiatien);
            imgmon=(ImageView)itemView.findViewById(R.id.imgmon);
            //Xử lý khi nút Chi tiết được bấm

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View   view=inflater.inflate(R.layout.item_danh_sach_quan_ly_menu,null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holde, int position) {

        QuanLyMenu quanLyMenu=list.get(position);
        holde.tvmamon.setText("Mã:"+"  "+quanLyMenu.getMamon());
        holde.tvtenmon.setText("Tên món:"+"  "+quanLyMenu.getTenmon());
        holde.tvgiatien.setText("Giá::"+"  "+quanLyMenu.getGia());
        Picasso.get().load(list.get(position).getAnhmon()).into(holde.imgmon);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }



}
