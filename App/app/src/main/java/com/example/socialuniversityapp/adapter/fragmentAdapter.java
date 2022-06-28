package com.example.socialuniversityapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.socialuniversityapp.ui.MainActivity;
import com.example.socialuniversityapp.ui.MajorPostActivity;
import com.example.socialuniversityapp.ui.MaterialActivity;
import com.example.socialuniversityapp.ui.UniversityPostActivity;

import java.util.ArrayList;

public class fragmentAdapter  extends FragmentStateAdapter {
private String []titles=new String[]{"Material","Major","University"};
//    private final ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
//    private final ArrayList<String> fragmentsTitle=new ArrayList<>();


    public fragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0 :{
                return new MaterialActivity();
            }
            case 1:
                return new MajorPostActivity();

            case 2:
                return new UniversityPostActivity();
        }
        return new MaterialActivity();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

//    public fragmentAdapter(@NonNull FragmentManager fm, int tabCount) {
//        super(fm,tabCount);
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        return fragmentArrayList.get(position);
//    }
//
//
//    @Override
//    public int getCount() {
//        return fragmentArrayList.size();
//    }
//
//    public void addFragment(Fragment fragment,String title){
//
//        fragmentArrayList.add(fragment);
//
//        fragmentsTitle.add(title);
//
//    }
//
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return fragmentsTitle.get(position);
//    }

}
