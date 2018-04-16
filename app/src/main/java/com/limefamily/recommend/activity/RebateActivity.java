package com.limefamily.recommend.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.limefamily.recommend.R;
import com.limefamily.recommend.adapter.RebatePagerAdapter;
import com.limefamily.recommend.logic.ApplicationBus;
import com.limefamily.recommend.logic.FragmentPageChangeEvent;


public class RebateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        RebatePagerAdapter rebatePagerAdapter = new RebatePagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(rebatePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ApplicationBus.getInstance().post(new FragmentPageChangeEvent(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void back(View view) {
        finish();
    }

}
