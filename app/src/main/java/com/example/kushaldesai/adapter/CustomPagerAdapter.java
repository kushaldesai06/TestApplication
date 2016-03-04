package com.example.kushaldesai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kushaldesai.Fragments.ImageFragment;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {
    FragmentManager fragmentManager;

    public CustomPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment tab1 = new ImageFragment(position);

        return tab1;
    }


    @Override
    public int getCount() {
        return 4;
    }
}