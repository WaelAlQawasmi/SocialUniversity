package com.example.socialuniversityapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class fragmentAdapter  extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    private final ArrayList<String> fragmentsTitle=new ArrayList<>();
    public fragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment,String title){

        fragmentArrayList.add(fragment);

        fragmentsTitle.add(title);

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}
