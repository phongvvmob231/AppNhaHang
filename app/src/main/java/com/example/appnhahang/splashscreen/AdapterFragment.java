package com.example.appnhahang.splashscreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterFragment extends FragmentStatePagerAdapter {
    public AdapterFragment(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new OnboardingFragment();
            case 1:
                return new OnboardingFragment1();
            case 2:
                return new OnboardingFragment2();
            default:
                return new OnboardingFragment();
        }


    }

    @Override
    public int getCount() {
        return 3;
    }


}
