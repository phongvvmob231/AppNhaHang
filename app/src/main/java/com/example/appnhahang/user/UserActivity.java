package com.example.appnhahang.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.appnhahang.R;
import com.example.appnhahang.login.LoginActivity;
import com.example.appnhahang.manage.NhanDonFragment;
import com.example.appnhahang.manage.TaiKhoanKHFragment;
import com.example.appnhahang.manage.ThongKeFragment;
import com.example.appnhahang.manage.menu.DanhSachMenuFragment;
import com.example.appnhahang.manage.table.DanhSachBanFragment;
import com.example.appnhahang.user.fragment.DoiMKFragment;
import com.example.appnhahang.user.fragment.GioiThieuFragment;
import com.example.appnhahang.user.fragment.LichSuFragment;
import com.example.appnhahang.user.fragment.LienHeFragment;
import com.example.appnhahang.user.fragment.MenuKHFragment;
import com.example.appnhahang.user.fragment.TTTaiKhoanFragment;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity {
    public static String tentaikhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        showSelectedFragment(new MenuKHFragment());
        // ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        tentaikhoan=b.getString("tentk");
        String sdtKh=b.getString("sdt");
        DrawerLayout drawer = findViewById(R.id.drawer_layout_user);
        NavigationView navigationView = findViewById(R.id.nav_view_user);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        View headerView = navigationView.getHeaderView(0);
        TextView tentk = (TextView) headerView.findViewById(R.id.tvtentk_kh);
        tentk.setText("TK:"+tentaikhoan);
        TextView sdt = (TextView) headerView.findViewById(R.id.tv_sdt_kh);
        sdt.setText("SĐT:"+sdtKh);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch ((item.getItemId())){
                    case  R.id.navigation_user_menu:
                        fragment=new MenuKHFragment();
                        setTitle("Menu");
                        break;
                    case  R.id.navigation_user_tk:
                        fragment=new TTTaiKhoanFragment();
                        setTitle("Thông Tin Tài Khoản");
                        break;
                    case  R.id.navigation_user_doimk:
                        fragment=new DoiMKFragment();
                        setTitle("Đổi Mật Khẩu");
                        break;
                    case  R.id.navigation_user_ls:
                        fragment=new LichSuFragment();
                        setTitle("Lịch Sử Giao Dịch");
                        break;
                    case  R.id.navigation_user_gioithieu:
                        fragment=new GioiThieuFragment();
                        setTitle("Giới Thiệu");
                        break;
                    case  R.id.navigation_user_trogiup:
                        fragment=new LienHeFragment();
                        setTitle("Liên Hệ");
                        break;
                    case  R.id.navigation_dangxuat:
                       Intent i=new Intent(UserActivity.this, LoginActivity.class);
                       startActivity(i);
                       finish();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_user,fragment).commit();
                return true;
            }
        });
    }
    private void showSelectedFragment (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_user,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}