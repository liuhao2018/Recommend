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
import com.limefamily.recommend.model.ChangePasswordModel;
import com.limefamily.recommend.model.User;
import com.limefamily.recommend.restful.AccountService;
import com.limefamily.recommend.restful.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PasswordManageActivity extends AppCompatActivity {


    private String oldPassword;
    private String newPassword;
    private Retrofit retrofit;
    private ProgressBar progressBar;
    private EditText oldPasswordEditView;
    private EditText newPasswordEditView;
    private EditText newPasswordSecondEditView;
    private TextView changePasswordSubmitTextView;

    public static final int PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manage);
        progressBar = findViewById(R.id.pb);
        oldPasswordEditView = findViewById(R.id.et_old_pwd);
        newPasswordEditView = findViewById(R.id.et_new_pwd);
        newPasswordSecondEditView = findViewById(R.id.et_new_pwd_second);
        changePasswordSubmitTextView = findViewById(R.id.tv_change_password_submit);
        changePasswordSubmitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPassword();
            }
        });
        retrofit = RecommendApplication.getInstance().getRetrofit();
    }

    private void verifyPassword() {
        oldPassword = oldPasswordEditView.getText().toString();
        if (TextUtils.isEmpty(oldPassword) || oldPassword.length() < PASSWORD_LENGTH) {
            Toast.makeText(this,getString(R.string.text_old_password_syntax_error),Toast.LENGTH_SHORT).show();
            oldPasswordEditView.setText(getString(R.string.text_empty));
            return;
        }
        newPassword = newPasswordEditView.getText().toString();
        if (TextUtils.isEmpty(newPassword) || newPassword.length() < PASSWORD_LENGTH) {
            Toast.makeText(this,getString(R.string.text_new_password_syntax_error),Toast.LENGTH_SHORT).show();
            newPasswordEditView.setText(getString(R.string.text_empty));
            return;
        }
        String newPasswordSecond = newPasswordSecondEditView.getText().toString();
        if (TextUtils.isEmpty(newPasswordSecond) || newPasswordSecond.length() < PASSWORD_LENGTH) {
            Toast.makeText(this,getString(R.string.text_new_password_syntax_error),Toast.LENGTH_SHORT).show();
            newPasswordSecondEditView.setText(getString(R.string.text_empty));
            return;
        }
        if (!newPassword.equals(newPasswordSecond)) {
            Toast.makeText(this,getString(R.string.text_new_password_different),Toast.LENGTH_SHORT).show();
            newPasswordEditView.setText(getString(R.string.text_empty));
            newPasswordSecondEditView.setText(getString(R.string.text_empty));
        }
        progressBar.setVisibility(View.VISIBLE);
        changePassword(oldPassword,newPassword);
    }

    private void changePassword(final String oldPassword, final String newPassword) {
        final String token = RecommendApplication.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(this,getString(R.string.text_change_password_failed),Toast.LENGTH_SHORT).show();
            finish();
        }
        final UserService userService = retrofit.create(UserService.class);
        Call<User> userCall = userService.view(String.format("%s %s",getString(R.string.text_prefix_token),token));
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null)  {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PasswordManageActivity.this,getString(R.string.text_change_password_failed),Toast.LENGTH_SHORT).show();
                    }else {
                        if (TextUtils.isEmpty(response.body().getName())) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(PasswordManageActivity.this,getString(R.string.text_change_password_failed),Toast.LENGTH_SHORT).show();
                        }else {
                            AccountService accountService = retrofit.create(AccountService.class);
                            Call<Void> accountCall = accountService.changePassword(String.format("%s %s",getString(R.string.text_prefix_token),token),
                                    new ChangePasswordModel(response.body().getMobile(),oldPassword,newPassword));
                            accountCall.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    progressBar.setVisibility(View.GONE);
                                    if (response.isSuccessful()) {
                                        Toast.makeText(PasswordManageActivity.this,getString(R.string.text_change_password_succeed),Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(PasswordManageActivity.this,getString(R.string.text_change_password_failed),Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(PasswordManageActivity.this,getString(R.string.text_change_password_failed),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }else {
                    Toast.makeText(PasswordManageActivity.this,getString(R.string.text_change_password_failed),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PasswordManageActivity.this,getString(R.string.text_change_password_failed),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void back(View view) {
        finish();
    }

}
