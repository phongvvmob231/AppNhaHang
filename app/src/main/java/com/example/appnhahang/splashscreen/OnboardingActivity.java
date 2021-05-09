package com.example.appnhahang.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appnhahang.R;

import me.relex.circleindicator.CircleIndicator;

public class OnboardingActivity extends AppCompatActivity {
    ViewPager pager2;
    TextView tv_skip;
    RelativeLayout relativeLayout;
    CircleIndicator circleIndicator;
    LinearLayout layoutNext;
    AdapterFragment fragment;
    ImageView img_anh_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        pager2=findViewById(R.id.vp1);
        tv_skip=findViewById(R.id.tv_skip);
        relativeLayout=findViewById(R.id.relatilayout1);
        circleIndicator=findViewById(R.id.crli);
        layoutNext=findViewById(R.id.next1);
        img_anh_next = findViewById(R.id.img_anh_next);
        fragment=new AdapterFragment(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager2.setAdapter(fragment);
        circleIndicator.setViewPager(pager2);

        pager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==2){
                    tv_skip.setVisibility(View.GONE);
                    layoutNext.setVisibility(View.GONE);
                }else {
                    tv_skip.setVisibility(View.VISIBLE);
                    layoutNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pager2.setCurrentItem(2);
            }
        });
        img_anh_next .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pager2.getCurrentItem()<2){
                    pager2.setCurrentItem(pager2.getCurrentItem()+1);
                }
            }
        });
    }
}