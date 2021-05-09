package com.example.appnhahang.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhahang.R;
import com.example.appnhahang.moder.Account;

import java.util.List;

public class QuanLyTKAdapter extends  RecyclerView.Adapter<QuanLyTKAdapter.ViewHolder> {
    List<Account> list;

    public Context context;
    LayoutInflater inflater;



    public QuanLyTKAdapter(Context context, List<Account> list) {
        this.list = list;
        this.context = context;

        inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        TextView tvtenTK,tvsdt;


        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            tvtenTK = itemView.findViewById(R.id.tvtenTK);
            tvsdt = itemView.findViewById(R.id.tvsdt);

            //Xử lý khi nút Chi tiết được bấm

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View   view=inflater.inflate(R.layout.item_danh_sach_quan_ly_tk,null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holde, int position) {

        Account taiKhoanKH=list.get(position);
        holde.tvtenTK.setText("Tài Khoản:"+"  "+taiKhoanKH.getTentk());
        holde.tvsdt.setText("SĐT:"+"  "+taiKhoanKH.getSdt());



    }


    @Override
    public int getItemCount() {
        return list.size();
    }




}
