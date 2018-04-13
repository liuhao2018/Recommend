package com.limefamily.recommend.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.model.LoginModel;
import com.limefamily.recommend.model.LoginResponse;
import com.limefamily.recommend.restful.AccountService;
import com.limefamily.recommend.util.PhoneUtil;
import com.limefamily.recommend.util.SPUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText mobileEditText;
    private EditText passwordEditText;
    private TextView submitTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobileEditText = findViewById(R.id.et_mobile);
        passwordEditText = findViewById(R.id.et_password);
        submitTextView = findViewById(R.id.tv_login_submit);
        progressBar = findViewById(R.id.pb);
        submitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String mobile = mobileEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this,getString(R.string.text_mobile_syntax_error),Toast.LENGTH_SHORT).show();
            mobileEditText.setText(getString(R.string.text_empty));
            return;
        }
        if (!PhoneUtil.isMobileExact(mobile)) {
            Toast.makeText(this,getString(R.string.text_mobile_syntax_error),Toast.LENGTH_SHORT).show();
            mobileEditText.setText(getString(R.string.text_empty));
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < PasswordManageActivity.PASSWORD_LENGTH ) {
            Toast.makeText(this,getString(R.string.text_password_syntax_error),Toast.LENGTH_SHORT).show();
            passwordEditText.setText(getString(R.string.text_empty));
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        AccountService accountService = retrofit.create(AccountService.class);
        Call<LoginResponse> call = accountService.login(new LoginModel(mobile,password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String token = response.body().getToken();
                        if (!TextUtils.isEmpty(token)) {
                            refreshToken(token);
                            Toast.makeText(LoginActivity.this,getString(R.string.text_login_succeed),Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,getString(R.string.text_login_failed),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this,getString(R.string.text_login_failed),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,getString(R.string.text_login_failed),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this,getString(R.string.text_login_failed),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshToken(String token) {
        SPUtil.getInstance(RecommendApplication.RECOMMEND_STORAGE_KIT).put(RecommendApplication.KEY_USER_TOKEN,token);
    }
}
