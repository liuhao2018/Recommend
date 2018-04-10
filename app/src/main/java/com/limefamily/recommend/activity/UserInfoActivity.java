package com.limefamily.recommend.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userNameTextView,userSexTextView,userBirthdayTextView;

    private SimpleDraweeView userHeadDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
    }

    private void initView() {
        userNameTextView = findViewById(R.id.tv_user_name);
        userHeadDraweeView = findViewById(R.id.iv_user_head);
        userSexTextView = findViewById(R.id.tv_user_sex);
        userBirthdayTextView = findViewById(R.id.tv_user_birthday);
        View userHeadView = findViewById(R.id.iv_user_head);
        View userNameView = findViewById(R.id.rl_user_name);
        View userSexView = findViewById(R.id.rl_user_sex);
        View userBirthdayView = findViewById(R.id.rl_user_birthday);
        userHeadView.setOnClickListener(this);
        userNameView.setOnClickListener(this);
        userSexView.setOnClickListener(this);
        userBirthdayView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_head:
                handleSelectUserHead();
                break;
            case R.id.rl_user_name:
                handleInputUserName();
                break;
            case R.id.rl_user_sex:
                handleInputUserSex();
                break;
            case R.id.rl_user_birthday:
                handleInputUserBirthday();
                break;
        }
    }

    private void handleSelectUserHead() {

    }

    private void handleInputUserName() {

    }

    private void handleInputUserSex() {

    }

    private void handleInputUserBirthday() {

    }

    public void back(View view) {
        finish();
    }
}
