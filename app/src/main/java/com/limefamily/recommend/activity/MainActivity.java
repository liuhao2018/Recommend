package com.limefamily.recommend.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.limefamily.recommend.R;
import com.limefamily.recommend.adapter.AppPagerAdapter;


/**
 * @author liuhao
 */
public class MainActivity extends AppCompatActivity {

    private final int TAB_HOME = 0;
    private final int TAB_USER = 2;
    private final int TAB_RECOMMEND = 1;

    private ViewPager mainViewPager;
    private AppPagerAdapter appPagerAdapter;
    private View homeTabView,recommendTabView,userTabView;
    private ImageView homeTabImageView,recommendTabImageView,userTabImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeTabView = findViewById(R.id.ll_home);
        recommendTabView = findViewById(R.id.ll_care);
        userTabView = findViewById(R.id.ll_mine);
        homeTabImageView = findViewById(R.id.iv_home);
        recommendTabImageView = findViewById(R.id.iv_care);
        userTabImageView = findViewById(R.id.iv_mine);
        mainViewPager = findViewById(R.id.view_pager);
        appPagerAdapter = new AppPagerAdapter(getSupportFragmentManager());
        homeTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTabClick(TAB_HOME);
            }
        });
        recommendTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTabClick(TAB_RECOMMEND);
            }
        });
        userTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTabClick(TAB_USER);
            }
        });
        mainViewPager.setAdapter(appPagerAdapter);
        mainViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateView(position);
            }
        });

        updateView(TAB_HOME);
    }

    private void handleTabClick(int tab) {
        mainViewPager.setCurrentItem(tab,false);
        updateView(tab);
    }

    private void updateView(int position) {
        homeTabImageView.setImageResource(R.mipmap.icon_home_normal);
        recommendTabImageView.setImageResource(R.mipmap.icon_care_normal);
        userTabImageView.setImageResource(R.mipmap.icon_mine_normal);
        if (position == TAB_HOME ) {
            homeTabImageView.setImageResource(R.mipmap.icon_home_select);
        }else if (position == TAB_RECOMMEND) {
            recommendTabImageView.setImageResource(R.mipmap.icon_care_select);
        }else if (position == TAB_USER) {
            userTabImageView.setImageResource(R.mipmap.icon_mine_select);
        }
    }
}

