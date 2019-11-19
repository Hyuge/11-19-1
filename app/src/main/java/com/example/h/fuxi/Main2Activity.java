package com.example.h.fuxi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ArrayList<Fragment> list;
    private MyPagerAdapter adapter;
    private String[] strings={"首页","收藏","百度"};
    private int[] in={R.drawable.my_selector,R.drawable.my_selector1,R.drawable.my_selector2};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main2);
        initView ( );
        initpager();
    }

    private void initpager() {
        OneFragment oneFragment = new OneFragment ( );
        TowFragment towFragment = new TowFragment ( );
        ThreeFragment threeFragment = new ThreeFragment ( );

        list = new ArrayList<> ( );
        list.add (oneFragment);
        list.add (towFragment);
        list.add (threeFragment);
        adapter = new MyPagerAdapter (getSupportFragmentManager ( ), list);
        mViewPager.setAdapter (adapter);
        mTabLayout.setupWithViewPager (mViewPager);
        for (int i = 0; i < list.size ( ); i++) {
            mTabLayout.getTabAt (i).setCustomView (getItemTab(i));

        }


    }

    private View getItemTab(int i) {
        View view = View.inflate (this, R.layout.layout_pager, null);
        TextView byId = view.findViewById (R.id.TextView);
        ImageView byId1 = view.findViewById (R.id.ImageView);
        byId.setText (strings[i]);
        byId1.setImageResource (in[i]);

        return view;
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById (R.id.ToolBar);
        mViewPager = (ViewPager) findViewById (R.id.ViewPager);
        mTabLayout = (TabLayout) findViewById (R.id.TabLayout);

    }
}
