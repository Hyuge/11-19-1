package com.example.h.fuxi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by H on 2019/10/31.
 */

public class MyPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> list;

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super (fm);
        this.list = list;
    }

    public MyPagerAdapter(FragmentManager fm) {
        super (fm);
    }


    @Override
    public Fragment getItem(int position) {
        return list.get (position);
    }

    @Override
    public int getCount() {
        return list.size ();
    }
}
