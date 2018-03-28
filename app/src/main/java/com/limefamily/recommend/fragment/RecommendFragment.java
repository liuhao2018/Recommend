package com.limefamily.recommend.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limefamily.recommend.R;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class RecommendFragment extends Fragment {

    public RecommendFragment() {
        super();
    }

    public static RecommendFragment newInstance() {
        RecommendFragment recommendFragment = new RecommendFragment();
        return recommendFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recommend_pager,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
