package com.limefamily.recommend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.limefamily.recommend.fragment.HomeFragment;
import com.limefamily.recommend.fragment.RecommendFragment;
import com.limefamily.recommend.fragment.UserFragment;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class AppPagerAdapter extends FragmentStatePagerAdapter {

    public static final int TAB_NUM = 3;

    public AppPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HomeFragment.newInstance();
        }
        else if (position == 1) {
            return RecommendFragment.newInstance();
        }
        return UserFragment.newInstance();
    }

    @Override
    public int getCount() {
        return TAB_NUM;
    }
}
