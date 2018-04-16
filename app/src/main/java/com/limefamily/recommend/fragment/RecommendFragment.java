package com.limefamily.recommend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.activity.LoginActivity;
import com.limefamily.recommend.activity.RebateActivity;
import com.limefamily.recommend.activity.RecommendNavigationActivity;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class RecommendFragment extends Fragment implements View.OnClickListener {

    private final int REQUEST_INTENT_LOGIN = 1;

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
        return inflater.inflate(R.layout.fragment_recommend_page,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View recommendCustomerView = view.findViewById(R.id.rl_recommend_customer);
        View recommendAttendantView = view.findViewById(R.id.rl_recommend_attendant);
        View rebateView = view.findViewById(R.id.rl_rebate);
        recommendCustomerView.setOnClickListener(this);
        recommendAttendantView.setOnClickListener(this);
        rebateView.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(RecommendApplication.getInstance().getToken())) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,REQUEST_INTENT_LOGIN);
            return;
        }
        switch (v.getId()) {
            case R.id.rl_recommend_customer:
                Intent recommendCustomerIntent = new Intent(getActivity(), RecommendNavigationActivity.class);
                recommendCustomerIntent.putExtra(RecommendNavigationActivity.KEY_RECOMMEND_TYPE,RecommendNavigationActivity.TYPE_CUSTOMER);
                startActivity(recommendCustomerIntent);
                break;
            case R.id.rl_recommend_attendant:
                Intent recommendAttendantIntent = new Intent(getActivity(), RecommendNavigationActivity.class);
                recommendAttendantIntent.putExtra(RecommendNavigationActivity.KEY_RECOMMEND_TYPE,RecommendNavigationActivity.TYPE_ATTENDANT);
                startActivity(recommendAttendantIntent);
                break;
            case R.id.rl_rebate:
                startActivity(new Intent(getActivity(), RebateActivity.class));
                break;
        }

    }
}
