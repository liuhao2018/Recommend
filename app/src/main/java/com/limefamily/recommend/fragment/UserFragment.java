package com.limefamily.recommend.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limefamily.recommend.R;
import com.limefamily.recommend.activity.AccountSecureActivity;
import com.limefamily.recommend.activity.LoginActivity;
import com.limefamily.recommend.activity.UserInfoActivity;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class UserFragment extends Fragment {

    private Context context;

    public UserFragment() {
        super();
        this.context = getContext();
    }

    public static UserFragment newInstance() {
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user_pager,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView loginStatusText = view.findViewById(R.id.tv_login_status);
        View userInfo = view.findViewById(R.id.rl_user_info);
        View accountSecure = view.findViewById(R.id.rl_account_secure);
        loginStatusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
            }
        });
        accountSecure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccountSecureActivity.class));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
