package com.limefamily.recommend.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.limefamily.recommend.R;
import com.limefamily.recommend.adapter.AppPagerAdapter;


/**
 * @author liuhao
 */
public class MainActivity extends AppCompatActivity {

    private ViewGroup btnHome,btnCare,btnMine;
    private ImageView ivHome,ivCare,ivMine;
    private ViewPager viewPager;

    private AppPagerAdapter appPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnHome = findViewById(R.id.ll_home);
        btnCare = findViewById(R.id.ll_care);
        btnMine = findViewById(R.id.ll_mine);
        ivHome = findViewById(R.id.iv_home);
        ivCare = findViewById(R.id.iv_care);
        ivMine = findViewById(R.id.iv_mine);
        viewPager = findViewById(R.id.view_pager);
        appPagerAdapter = new AppPagerAdapter(getSupportFragmentManager());
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,false);
                updateView(0);
            }
        });
        btnCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,false);
                updateView(1);
            }
        });
        btnMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2,false);
                updateView(2);
            }
        });
        viewPager.setAdapter(appPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateView(0);
    }

    private void updateView(int position) {
        ivHome.setImageResource(R.mipmap.icon_home_normal);
        ivCare.setImageResource(R.mipmap.icon_care_normal);
        ivMine.setImageResource(R.mipmap.icon_mine_normal);
        if (position == 0 ) {
            ivHome.setImageResource(R.mipmap.icon_home_select);
        }else if (position == 1) {
            ivCare.setImageResource(R.mipmap.icon_care_select);
        }else  {
            ivMine.setImageResource(R.mipmap.icon_mine_select);
        }
    }
}

