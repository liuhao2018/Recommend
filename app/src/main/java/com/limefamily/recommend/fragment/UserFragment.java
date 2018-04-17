package com.limefamily.recommend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.activity.AccountSecureActivity;
import com.limefamily.recommend.activity.LoginActivity;
import com.limefamily.recommend.activity.UserInfoActivity;
import com.limefamily.recommend.model.User;
import com.limefamily.recommend.restful.UserService;
import com.limefamily.recommend.util.FormatUtil;
import com.limefamily.recommend.util.SPUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class UserFragment extends Fragment implements OnClickListener {

    private SimpleDraweeView userHeadImageView;
    private ViewGroup userDescribeViewGroup,userInfo,accountSecure;
    private TextView userLoginStatusTextView,userNameTextView,userMobileTextView;

    private final int REQUEST_INTENT_LOGIN = 2;

    public UserFragment() {
        super();
    }

    public static UserFragment newInstance() {
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_page,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userLoginStatusTextView = view.findViewById(R.id.tv_login_status);
        userHeadImageView = view.findViewById(R.id.iv_user_head);
        userNameTextView = view.findViewById(R.id.tv_user_name);
        userMobileTextView = view.findViewById(R.id.tv_user_mobile);
        userDescribeViewGroup = view.findViewById(R.id.ll_user_desc);
        userInfo = view.findViewById(R.id.rl_user_info);
        accountSecure = view.findViewById(R.id.rl_account_secure);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userHeadImageView.setOnClickListener(this);
        userInfo.setOnClickListener(this);
        accountSecure.setOnClickListener(this);
        userLoginStatusTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginOrLogout();
            }
        });
        updateLoginStatus();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_INTENT_LOGIN) {
                updateLoginStatus();
            }
        }
    }

    private void handleLoginOrLogout() {
        String token = RecommendApplication.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,REQUEST_INTENT_LOGIN);
        }else {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.text_logout)
                    .content(R.string.text_real_logout)
                    .positiveText(R.string.text_confirm)
                    .negativeText(R.string.text_cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            deleteToken();
                            updateLogoutView();
                            Toast.makeText(getActivity(),getString(R.string.text_already_logout),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    })
                    .build()
                    .show();
        }
    }

    private void deleteToken() {
        SPUtil.getInstance(RecommendApplication.RECOMMEND_STORAGE_KIT).put(RecommendApplication.KEY_USER_TOKEN,"");
    }


    private void updateLoginStatus() {
        String token = RecommendApplication.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            updateView(false,null);
        }else {
            updateView(true,token);
        }
    }

    private void updateView(boolean isLogin,String token) {
        if (isLogin) {
            updateLoginView(token);
        }else {
            updateLogoutView();
        }
    }

    private void updateLoginView(String token) {
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);
        Call<User> call = userService.view(String.format("%s %s",getString(R.string.text_prefix_token),token));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userLoginStatusTextView.setText(getString(R.string.text_logout));
                    if (response.body() != null) {
                        String userHead = response.body().getAvatar();
                        if (!TextUtils.isEmpty(userHead)) {
                            userHeadImageView.setImageURI(userHead);
                        }else {
                            userHeadImageView.setImageResource(R.mipmap.icon_user_head_default);
                        }
                        String name = response.body().getName();
                        String mobile = response.body().getMobile();
                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile)) {
                            userDescribeViewGroup.setVisibility(View.VISIBLE);
                            userNameTextView.setText(name);
                            userMobileTextView.setText(FormatUtil.getInstance().formatMobile(mobile));
                        }
                    }else {
                        Toast.makeText(getActivity(),getString(R.string.text_fetch_user_info_failed),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),getString(R.string.text_fetch_user_info_failed),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.text_fetch_user_info_failed),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLogoutView() {
        userLoginStatusTextView.setText(getString(R.string.text_login));
        userHeadImageView.setImageResource(R.mipmap.icon_user_head_default);
        userDescribeViewGroup.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(RecommendApplication.getInstance().getToken())) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,REQUEST_INTENT_LOGIN);
            return;
        }
        switch (v.getId()) {
            case R.id.iv_user_head:
            case R.id.rl_user_info:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.rl_account_secure:
                startActivity(new Intent(getActivity(), AccountSecureActivity.class));
                break;
        }
    }
}
