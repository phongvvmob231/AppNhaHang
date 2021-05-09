package com.example.appnhahang;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.appnhahang.login.LoginActivity;
import com.example.appnhahang.manage.table.DanhSachBanFragment;
import com.example.appnhahang.manage.menu.DanhSachMenuFragment;
import com.example.appnhahang.manage.NhanDonFragment;
import com.example.appnhahang.manage.TaiKhoanKHFragment;
import com.example.appnhahang.manage.ThongKeFragment;
import com.example.appnhahang.user.UserActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

public static String tentaikhoanAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        tentaikhoanAD=b.getString("tentk");
        String sdtAdmin=b.getString("sdt");
        // ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);
        TextView tentk = (TextView) headerView.findViewById(R.id.tvTentk_admin);
        tentk.setText("TK:"+tentaikhoanAD);
        TextView sdt = (TextView) headerView.findViewById(R.id.tvsdt_admin);
        sdt.setText("SĐT:"+sdtAdmin);
        showSelectedFragment(new NhanDonFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch ((item.getItemId())){
                    case  R.id.navigation_nhandon:
                        fragment=new NhanDonFragment();
                        setTitle("Nhận Đơn");
                        break;
                    case  R.id.navigation_ds_ban:
                        fragment=new DanhSachBanFragment();
                        setTitle("Danh Sách Bàn");
                        break;
                    case  R.id.navigation_ds_menu:
                        fragment=new DanhSachMenuFragment();
                        setTitle("Danh Sách Menu");
                        break;
                    case  R.id.navigation_taikhoan:
                        fragment=new TaiKhoanKHFragment();
                        setTitle("Tài Khoản Khách Hàng");
                        break;
                    case  R.id.navigation_thongke:
                        fragment=new ThongKeFragment();
                        setTitle("Thống Kê");
                        break;
                    case  R.id.navigation_dangxuatql:
                        Intent i=new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
                return true;
            }
        });

    }




    private void showSelectedFragment (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}