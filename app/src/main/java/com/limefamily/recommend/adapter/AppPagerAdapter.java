package com.limefamily.recommend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limefamily.recommend.activity.MainActivity;
import com.limefamily.recommend.fragment.HomeFragment;
import com.limefamily.recommend.fragment.RecommendFragment;
import com.limefamily.recommend.fragment.UserFragment;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class AppPagerAdapter extends FragmentStatePagerAdapter {

    public AppPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == MainActivity.TAB_HOME) {
            return HomeFragment.newInstance();
        }
        else if (position == MainActivity.TAB_RECOMMEND) {
            return RecommendFragment.newInstance();
        }
        return UserFragment.newInstance();
    }

    @Override
    public int getCount() {
        return MainActivity.TAB_NUMBER;
    }
}
