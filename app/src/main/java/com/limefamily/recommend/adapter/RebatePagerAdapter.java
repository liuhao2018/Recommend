package com.limefamily.recommend.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limefamily.recommend.R;
import com.limefamily.recommend.fragment.RebateFragment;

/**
 * Created by liuhao on 2018/4/16.
 */

public class RebatePagerAdapter extends FragmentStatePagerAdapter {

    private String[] rebateType;

    public RebatePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        rebateType = context.getResources().getStringArray(R.array.array_rebate_type);
    }

    @Override
    public Fragment getItem(int position) {
        return RebateFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return rebateType[position];
    }

    @Override
    public int getCount() {
        return rebateType.length;
    }
}
